package tip.capstone.mathuto.fragments.lesson4

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import tip.capstone.mathuto.FullScreenImageAdapter
import tip.capstone.mathuto.R
import tip.capstone.mathuto.databinding.Lesson1OverviewBinding
import tip.capstone.mathuto.databinding.Lesson2OverviewBinding
import tip.capstone.mathuto.databinding.Lesson4OverviewBinding
import tip.capstone.mathuto.quiz.Quiz1Activity
import tip.capstone.mathuto.quiz.Quiz4Activity
import tip.capstone.mathuto.tutorial.Tutorial1Activity
import tip.capstone.mathuto.tutorial.Tutorial4Activity
import java.util.*

class OverviewFragment : Fragment(), TextToSpeech.OnInitListener {

    private lateinit var binding: Lesson4OverviewBinding
    private lateinit var tts: TextToSpeech

    private var isReadingAloud = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Lesson4OverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tts = TextToSpeech(requireContext(), this)

        binding.btnTts.setOnClickListener {
            val description1 = binding.description1.text.toString()
            val mDescription = "$description1"

            if (mDescription.isNotEmpty()) {
                if (isReadingAloud) {
                    tts.stop()
                    isReadingAloud = false
                    binding.btnTts.text = "Read Aloud"
                } else {
                    tts.speak(mDescription, TextToSpeech.QUEUE_FLUSH, null, null)
                    isReadingAloud = true
                    binding.btnTts.text = "Stop Read Aloud"
                }
            }
        }
        binding.btnTutorial.setOnClickListener {
            val intent = Intent(requireActivity(), Tutorial4Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            requireActivity().startActivity(intent)
        }
        binding.btnStartQuiz.setOnClickListener {
            val intent = Intent(requireActivity(), Quiz4Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            requireActivity().startActivity(intent)
        }

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language data is missing or the language is not supported.
                // You can handle this according to your app's requirements.
            }
        } else {
            // Failed to initialize the TextToSpeech engine.
            // You can handle this according to your app's requirements.
        }
        binding.btnTts.isEnabled = status == TextToSpeech.SUCCESS
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.stop()
        tts.shutdown()
    }
}