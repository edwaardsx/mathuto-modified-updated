package tip.capstone.mathuto.quiz

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import tip.capstone.mathuto.MainActivity.Companion.QUIZ5_PASSED
import tip.capstone.mathuto.R
import tip.capstone.mathuto.databinding.Quiz5Binding
import tip.capstone.mathuto.questions.Question5
import tip.capstone.mathuto.questions.Question5.CORRECT_ANS
import tip.capstone.mathuto.questions.Question5.SELECTED_ANSWERS
import tip.capstone.mathuto.questions.Question5.TOTAL_QUESTIONS
import tip.capstone.mathuto.questions.Question5.WRONG_ANS
import tip.capstone.mathuto.quiz.result.Result5Activity
import tip.capstone.mathuto.sqlite.SQLiteHelper
import tip.capstone.mathuto.sqlite.TrueOrFalse
import java.util.*


@Suppress("DEPRECATION")
class Quiz5Activity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: Quiz5Binding
    private lateinit var selectedAnswer: ArrayList<Int>
    private lateinit var db: SQLiteHelper

    private var mCurrentPosition: Int = 1
    private var mTrueOrFalseList: ArrayList<TrueOrFalse>? = null
    private val trueOrFalseListArrangement: ArrayList<TrueOrFalse>? =  null
    private val mMaxQuestions = 10

    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mWrongAnswers: Int = 0
    //private var mUnansweredQuestion: Int = 0
    private var areOptionsEnabled = true

    private val handler = Handler()
    private val delayDuration: Long = 2000
    private var remainingTime: Long = 60000

    private var seCorrect: MediaPlayer? = null
    private var seWrong: MediaPlayer? = null
    private var seBackgroundMusic: MediaPlayer? = null
    private var isBackgroundMusicPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = Quiz5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#303030")

        db = SQLiteHelper(this)
        binding.progressBar.max = mMaxQuestions

        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)

        seCorrect = MediaPlayer.create(this, R.raw.sound_effect_correct)
        seWrong = MediaPlayer.create(this, R.raw.sound_effect_wrong)
        seBackgroundMusic = MediaPlayer.create(this, R.raw.sound_background_music)

        mTrueOrFalseList = Question5.getQuestions()
        mTrueOrFalseList?.shuffle()
        setQuestion()

        selectedAnswer = ArrayList()

        seBackgroundMusic?.setOnCompletionListener {
            if (isBackgroundMusicPlaying) {
                seBackgroundMusic?.start()
            }
        }
        startBackgroundMusic()
    }

    private fun startBackgroundMusic() {
        if (!isBackgroundMusicPlaying) {
            seBackgroundMusic?.start()
            isBackgroundMusicPlaying = true
        }
    }

    private fun stopBackgroundMusic() {
        if (isBackgroundMusicPlaying) {
            seBackgroundMusic?.pause()
            seBackgroundMusic?.seekTo(0)
            isBackgroundMusicPlaying = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopBackgroundMusic()
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        defaultOptionView()
        if (mCurrentPosition <= mTrueOrFalseList!!.size) {
            timer.start()
            if (!areOptionsEnabled) {
                disableOptions()
            } else {
                enableOptions()
            }
            val trueOrFalse: TrueOrFalse = mTrueOrFalseList!![mCurrentPosition - 1]
            binding.progressBar.progress = mCurrentPosition
            binding.tvProgress.text = "Question $mCurrentPosition/${binding.progressBar.max}"
            binding.tvQuestion.text = trueOrFalse.question
            binding.tvQuestion.typeface = ResourcesCompat.getFont(this, R.font.geologica_regular)
            binding.tvOptionOne.text = trueOrFalse.optionA
            binding.tvOptionTwo.text = trueOrFalse.optionB
            trueOrFalseListArrangement?.add(trueOrFalse)

            db.insertQuestion2(trueOrFalse);
            println("INSERTION NG QUESTION: " + trueOrFalse)
            /*println("QuestionListArrangement: " + (QuestionListArrangement?.get(mCurrentPosition) ?: question))*/

        }
    }

    private fun disableOptions() {
        binding.tvOptionOne.isEnabled = false
        binding.tvOptionTwo.isEnabled = false
    }
    private fun enableOptions() {
        binding.tvOptionOne.isEnabled = true
        binding.tvOptionTwo.isEnabled = true
    }

    private fun defaultOptionView() {
        val options = ArrayList<TextView>()
        binding.tvOptionOne.let {
            options.add(0, it)
        }
        binding.tvOptionTwo.let {
            options.add(1, it)
        }
        for (option in options) {
            option.setTextColor(Color.parseColor("#FFFFFF"))
            option.typeface = ResourcesCompat.getFont(this, R.font.geologica_regular)
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.quiz_default_option_border_bg)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.quiz_default_option_border_bg)
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_option_one -> {
                selectedOptionView(binding.tvOptionOne, 1)
                processOptionSelected()
            }
            R.id.tv_option_two -> {
                selectedOptionView(binding.tvOptionTwo, 2)
                processOptionSelected()
            }
        }
    }

    private fun processOptionSelected() {
        areOptionsEnabled = false
        timer.cancel()
        disableOptions()
        val question = mTrueOrFalseList?.get(mCurrentPosition - 1)
        selectedAnswer.add(mSelectedOptionPosition)
        if (question!!.correctAnswer == mSelectedOptionPosition) {
            answerView(question.correctAnswer, R.drawable.quiz_correct_option_border_bg)
            mCorrectAnswers++
            seCorrect?.start()
            mTrueOrFalseList
        } else {
            answerView(mSelectedOptionPosition, R.drawable.quiz_wrong_option_border_bg)
            mWrongAnswers++
            seWrong?.start()
        }
        if (mCurrentPosition == mTrueOrFalseList!!.size || mCurrentPosition >= mMaxQuestions) {
            handler.postDelayed({
                val intent = Intent(applicationContext, Result5Activity::class.java)
                seBackgroundMusic?.stop()
                intent.putExtra(CORRECT_ANS, mCorrectAnswers)

                val scores = db.getAllHighScores()
                if(scores.size < 5){
                    db.insertHighScores("Lesson 5", mCorrectAnswers.toString())
                }else{
                    if (mCorrectAnswers > Integer.parseInt(scores[4].score)) {
                        // Sort the scores in descending order using QuickSort
                        val scoreList = ArrayList<Int>()
                        for (scoreEntry in scores) {
                            scoreList.add(Integer.parseInt(scoreEntry.score))
                        }
                        quickSort(scoreList, 0, scoreList.size - 1)

                        // Update the high scores
                        val highestScore = scoreList[0]
                        if (mCorrectAnswers > highestScore) {
                            db.updateHighScores("Lesson 5", mCorrectAnswers.toString())
                        }
                    }
                }
                if(mCorrectAnswers >= 5) {
                    QUIZ5_PASSED = true
                }

                val qList = db.getAllQuestions2();

                for(question in qList){
                    println("QUESTION ARRANGEMENT: " + question)
                }

                intent.putExtra(TOTAL_QUESTIONS, mMaxQuestions)
                intent.putExtra(WRONG_ANS, mMaxQuestions - (mCorrectAnswers))
                //intent.putExtra(WRONG_ANS, mQuestionList!!.size - (mCorrectAnswers + mUnansweredQuestion))
                //intent.putExtra(UNANSWERED_QUESTIONS, mQuestionList!!.size - (mCorrectAnswers + mWrongAnswers))
                intent.putExtra(CORRECT_ANS, mCorrectAnswers)
                intent.putIntegerArrayListExtra(SELECTED_ANSWERS, selectedAnswer)
                intent.putExtra(CORRECT_ANS, mCorrectAnswers)

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent)
                overridePendingTransition(0, 0)
            }, delayDuration)
        } else {
            handler.postDelayed({
                mCurrentPosition++
                areOptionsEnabled = true
                setQuestion()
            }, delayDuration)
        }
        mSelectedOptionPosition = 0
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                binding.tvOptionOne.background = ContextCompat.getDrawable(
                    this, drawableView)
            }
            2 -> {
                binding.tvOptionTwo.background = ContextCompat.getDrawable(
                    this, drawableView)
            }
        }
    }

    private val timer = object : CountDownTimer(remainingTime, 1000) {
        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {
            remainingTime = millisUntilFinished
            val seconds = millisUntilFinished / 1000
            val minutes = seconds / 60
            val remainingSeconds = seconds % 60
            binding.tvTimer.text = "Time Left: ${minutes}:${String.format("%02d", remainingSeconds)}"
        }

        override fun onFinish() {
            areOptionsEnabled = false
            disableOptions()
            val question = mTrueOrFalseList?.get(mCurrentPosition - 1)
            val wrongAnswer = getWrongAnswer(question?.correctAnswer ?: 0)
            selectedAnswer.add(wrongAnswer)
            mWrongAnswers++
            seWrong?.start()
            answerView(wrongAnswer, R.drawable.quiz_wrong_option_border_bg) // Highlight the wrong answer option
            answerView(question?.correctAnswer ?: 0, R.drawable.quiz_correct_option_border_bg) // Highlight the correct answer as wrong
            handler.postDelayed({
                mCurrentPosition++
                areOptionsEnabled = true
                setQuestion()
            }, delayDuration)
        }
    }

    private fun getWrongAnswer(correctAnswer: Int): Int {
        val options = arrayListOf(1, 2)
        options.remove(correctAnswer)
        return options.random()
    }

    private fun quickSort(scores: ArrayList<Int>, low: Int, high: Int) {
        if (low < high) {
            val pivotIndex = partition(scores, low, high)
            quickSort(scores, low, pivotIndex - 1)
            quickSort(scores, pivotIndex + 1, high)
        }
    }

    private fun partition(scores: ArrayList<Int>, low: Int, high: Int): Int {
        val pivot = scores[high]
        var i = low - 1

        for (j in low until high) {
            if (scores[j] > pivot) {
                i++
                swap(scores, i, j)
            }
        }

        swap(scores, i + 1, high)
        return i + 1
    }

    private fun swap(scores: ArrayList<Int>, i: Int, j: Int) {
        val temp = scores[i]
        scores[i] = scores[j]
        scores[j] = temp
    }
}