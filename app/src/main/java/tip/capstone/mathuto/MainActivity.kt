package tip.capstone.mathuto

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import tip.capstone.mathuto.databinding.ActivityMainBinding
import tip.capstone.mathuto.lessons.*
import tip.capstone.mathuto.questions.LessonList
import tip.capstone.mathuto.recycler.RecyclerViewAdapter
import tip.capstone.mathuto.sqlite.LessonItem
import tip.capstone.mathuto.sqlite.MultipleChoice
import tip.capstone.mathuto.sqlite.SQLiteHelper
import tip.capstone.mathuto.tips.Tips
import tip.capstone.mathuto.tips.TipsList
import java.util.ArrayList

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener, SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var carouselView: CarouselView
    private lateinit var recyclerAdapter: RecyclerViewAdapter
    private lateinit var searchView: SearchView
    private lateinit var db: SQLiteHelper
    private lateinit var lessonItemList: List<LessonItem>

    private var isSearching = false
    private val mMaxLessons = 19

    private val carouselImages = intArrayOf(
        R.drawable.carousel_1,
        R.drawable.carousel_2,
        R.drawable.carousel_3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#F1F6F9")
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        binding.progressBar.max = mMaxLessons
        setProgressBar()

        carouselView = findViewById(R.id.carouselView)
        carouselView.pageCount = carouselImages.size
        carouselView.setImageListener(imageListener)

        /*BottomSheetBehavior.from(binding.bottomSheetLayout).apply {
            peekHeight = 400
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }*/

        db = SQLiteHelper(this)
        lessonItemList = LessonList.getLessons(db)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerAdapter = RecyclerViewAdapter(lessonItemList, this)
        recyclerView.adapter = recyclerAdapter

        /*val savedLessonItems = retrieveSavedLessonItemsData()
        if (savedLessonItems.isNotEmpty()) {
            lessonItemList = savedLessonItems
        }*/

        binding.listOfQuizScore.setOnClickListener{
            val intent = Intent(applicationContext, MainScoresActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            applicationContext.startActivity(intent)
            overridePendingTransition(0, 0)
        }

        binding.tips.setOnClickListener{
            val tips = TipsList.getTips().random()
            showTipsDialog(tips)
        }

        binding.aboutApp.setOnClickListener{
            val intent = Intent(applicationContext, AboutActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            applicationContext.startActivity(intent)
            overridePendingTransition(0, 0)
        }

        /*val dataList = listOf(
            if(QUIZ1_PASSED)
                Data(R.drawable.recycler_img_1,
                    "Addition and Subtraction of Fractions",
                    "Lesson 1", COMPLETED
                )
            else
                Data(R.drawable.recycler_img_1,
                    "Addition and Subtraction of Fractions",
                    "Lesson 1", IN_PROGRESS
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ2_PASSED)
                Data(R.drawable.recycler_img_2,
                    "Problem Solving Involving Addition and Subtraction of Fractions",
                    "Lesson 2",
                    COMPLETED
                )
            else if(QUIZ1_PASSED)
                Data(R.drawable.recycler_img_2,
                    "Problem Solving Involving Addition and Subtraction of Fractions",
                    "Lesson 2",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_2,
                    "Problem Solving Involving Addition and Subtraction of Fractions",
                    "Lesson 2",
                    LOCK
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ3_PASSED)
                Data(R.drawable.recycler_img_3,
                    "Multiplication of Simple Fractions and Mixed Fractions",
                    "Lesson 3",
                    COMPLETED
                )
            else if(QUIZ2_PASSED)
                Data(R.drawable.recycler_img_3,
                    "Multiplication of Simple Fractions and Mixed Fractions",
                    "Lesson 3",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_3,
                    "Multiplication of Simple Fractions and Mixed Fractions",
                    "Lesson 3",
                    LOCK
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ4_PASSED)
                Data(R.drawable.recycler_img_4,
                    "Problem Solving on Multiplication of Fractions",
                    "Lesson 4",
                    COMPLETED
                )
            else if(QUIZ3_PASSED)
                Data(R.drawable.recycler_img_4,
                    "Problem Solving on Multiplication of Fractions",
                    "Lesson 4",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_4,
                    "Problem Solving on Multiplication of Fractions",
                    "Lesson 4",
                    LOCK
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ5_PASSED)
                Data(R.drawable.recycler_img_5,
                    "Dividing Simple Fractions and Mixed Fractions",
                    "Lesson 5",
                    COMPLETED
                )
            else if(QUIZ4_PASSED)
                Data(R.drawable.recycler_img_5,
                    "Dividing Simple Fractions and Mixed Fractions",
                    "Lesson 5",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_5,
                    "Dividing Simple Fractions and Mixed Fractions",
                    "Lesson 5",
                    LOCK
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ6_PASSED)
                Data(R.drawable.recycler_img_1,
                    "Solving Routine or Non-Routine Problems Involving Division Without or With Any of the Other Operations of Fractions and Mixed Fractions",
                    "Lesson 6",
                    COMPLETED
                )
            else if(QUIZ5_PASSED)
                Data(R.drawable.recycler_img_1,
                    "Solving Routine or Non-Routine Problems Involving Division Without or With Any of the Other Operations of Fractions and Mixed Fractions",
                    "Lesson 6",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_1,
                    "Solving Routine or Non-Routine Problems Involving Division Without or With Any of the Other Operations of Fractions and Mixed Fractions",
                    "Lesson 6",
                    LOCK
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ7_PASSED)
                Data(R.drawable.recycler_img_2,
                    "Addition and Subtraction of Decimals",
                    "Lesson 7",
                    COMPLETED
                )
            else if(QUIZ6_PASSED)
                Data(R.drawable.recycler_img_2,
                    "Addition and Subtraction of Decimals",
                    "Lesson 7",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_2,
                    "Addition and Subtraction of Decimals",
                    "Lesson 7",
                    LOCK
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ8_PASSED)
                Data(R.drawable.recycler_img_3,
                    "Solving Routine or Non-Routine Problems Involving Addition and Subtraction of Decimals and Mixed Decimals Using Appropriate Problem Solving Strategies and Tools",
                    "Lesson 8",
                    COMPLETED
                )
            else if(QUIZ7_PASSED)
                Data(R.drawable.recycler_img_3,
                    "Solving Routine or Non-Routine Problems Involving Addition and Subtraction of Decimals and Mixed Decimals Using Appropriate Problem Solving Strategies and Tools",
                    "Lesson 8",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_3,
                    "Solving Routine or Non-Routine Problems Involving Addition and Subtraction of Decimals and Mixed Decimals Using Appropriate Problem Solving Strategies and Tools",
                    "Lesson 8",
                    LOCK
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ9_PASSED)
                Data(R.drawable.recycler_img_4,
                    "Multiplication of Decimals and Mixed Decimals",
                    "Lesson 9",
                    COMPLETED
                )
            else if(QUIZ8_PASSED)
                Data(R.drawable.recycler_img_4,
                    "Multiplication of Decimals and Mixed Decimals",
                    "Lesson 9",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_4,
                    "Multiplication of Decimals and Mixed Decimals",
                    "Lesson 9",
                    LOCK
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ10_PASSED)
                Data(R.drawable.recycler_img_5,
                    "Multiplication of Decimals and Mixed Decimals Mentally by 0.1, 0.01, 10 and 100",
                    "Lesson 10",
                    COMPLETED
                )
            else if(QUIZ9_PASSED)
                Data(R.drawable.recycler_img_5,
                    "Multiplication of Decimals and Mixed Decimals Mentally by 0.1, 0.01, 10 and 100",
                    "Lesson 10",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_5,
                    "Multiplication of Decimals and Mixed Decimals Mentally by 0.1, 0.01, 10 and 100",
                    "Lesson 10",
                    LOCK
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ11_PASSED)
                Data(R.drawable.recycler_img_1,
                    "Problem-Solving on Multiplication of Decimals",
                    "Lesson 11",
                    COMPLETED
                )
            else if(QUIZ10_PASSED)
                Data(R.drawable.recycler_img_1,
                    "Problem-Solving on Multiplication of Decimals",
                    "Lesson 11",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_1,
                    "Problem-Solving on Multiplication of Decimals",
                    "Lesson 11",
                    LOCK
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ12_PASSED)
                Data(R.drawable.recycler_img_2,
                    "Multi-Step Problems Involving Multiplication and Addition or Subtraction of Decimals",
                    "Lesson 12",
                    COMPLETED
                )
            else if(QUIZ11_PASSED)
                Data(R.drawable.recycler_img_2,
                    "Multi-Step Problems Involving Multiplication and Addition or Subtraction of Decimals",
                    "Lesson 12",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_2,
                    "Multi-Step Problems Involving Multiplication and Addition or Subtraction of Decimals",
                    "Lesson 12",
                    LOCK
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ13_PASSED)
                Data(R.drawable.recycler_img_3,
                    "Dividing Whole Numbers by Decimals Up 2 Decimal Places",
                    "Lesson 13",
                    COMPLETED
                )
            else if(QUIZ12_PASSED)
                Data(R.drawable.recycler_img_3,
                    "Dividing Whole Numbers by Decimals Up 2 Decimal Places",
                    "Lesson 13",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_3,
                    "Dividing Whole Numbers by Decimals Up 2 Decimal Places",
                    "Lesson 13",
                    LOCK
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ14_PASSED)
                Data(R.drawable.recycler_img_4,
                    "Dividing Decimals and Mixed Decimals",
                    "Lesson 14",
                    COMPLETED
                )
            else if(QUIZ13_PASSED)
                Data(R.drawable.recycler_img_4,
                    "Dividing Decimals and Mixed Decimals",
                    "Lesson 14",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_4,
                    "Dividing Decimals and Mixed Decimals",
                    "Lesson 14",
                    LOCK
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ15_PASSED)
                Data(R.drawable.recycler_img_5,
                    "Dividing Decimals Up to 4 Decimal Places by 0.1, 0.01 and 0.001",
                    "Lesson 15",
                    COMPLETED
                )
            else if(QUIZ14_PASSED)
                Data(R.drawable.recycler_img_5,
                    "Dividing Decimals Up to 4 Decimal Places by 0.1, 0.01 and 0.001",
                    "Lesson 15",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_5,
                    "Dividing Decimals Up to 4 Decimal Places by 0.1, 0.01 and 0.001",
                    "Lesson 15",
                    LOCK
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ16_PASSED)
                Data(R.drawable.recycler_img_1,
                    "Dividing Decimals Up to 2 Decimal Places by 10, 100 and 1000 Mentally",
                    "Lesson 16",
                    COMPLETED
                )
            else if(QUIZ15_PASSED)
                Data(R.drawable.recycler_img_1,
                    "Dividing Decimals Up to 2 Decimal Places by 10, 100 and 1000 Mentally",
                    "Lesson 16",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_1,
                    "Dividing Decimals Up to 2 Decimal Places by 10, 100 and 1000 Mentally",
                    "Lesson 16",
                    LOCK
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ17_PASSED)
                Data(R.drawable.recycler_img_2,
                    "Differentiating Repeating from Terminating and Non-Terminating Decimal Quotient",
                    "Lesson 17",
                    COMPLETED
                )
            else if(QUIZ16_PASSED)
                Data(R.drawable.recycler_img_2,
                    "Differentiating Repeating from Terminating and Non-Terminating Decimal Quotient",
                    "Lesson 17",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_2,
                    "Differentiating Repeating from Terminating and Non-Terminating Decimal Quotient",
                    "Lesson 17",
                    LOCK
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ18_PASSED)
                Data(R.drawable.recycler_img_3,
                    "Solving Word Problems Involving Division of Decimals",
                    "Lesson 18",
                    COMPLETED
                )
            else if(QUIZ17_PASSED)
                Data(R.drawable.recycler_img_3,
                    "Solving Word Problems Involving Division of Decimals",
                    "Lesson 18",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_3,
                    "Solving Word Problems Involving Division of Decimals",
                    "Lesson 18",
                    LOCK
                ),
////////////////////////////////////////////////////////////////////////////////////////////////////
            if(QUIZ19_PASSED)
                Data(R.drawable.recycler_img_4,
                    "Solving Multi-Step Problems Involving Division of Decimals and Any of the Other Operations",
                    "Lesson 19",
                    COMPLETED
                )
            else if(QUIZ18_PASSED)
                Data(R.drawable.recycler_img_4,
                    "Solving Multi-Step Problems Involving Division of Decimals and Any of the Other Operations",
                    "Lesson 19",
                    IN_PROGRESS
                )
            else
                Data(R.drawable.recycler_img_4,
                    "Solving Multi-Step Problems Involving Division of Decimals and Any of the Other Operations",
                    "Lesson 19",
                    LOCK
                ),
        )*/


        /*val adapter = RecyclerViewAdapter(dataList, this)
        recyclerAdapter = adapter

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)*/

    }

    /*override fun onDestroy() {
        super.onDestroy()
        // Save the lesson items data when the app is destroyed
        saveLessonItemsData()
    }

    private fun saveLessonItemsData() {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("lessonItemsData", Gson().toJson(lessonItemList))
        editor.apply()
    }

    private fun retrieveSavedLessonItemsData(): List<LessonItem> {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val lessonItemsData = sharedPreferences.getString("lessonItemsData", null)

        if (!lessonItemsData.isNullOrEmpty()) {
            return Gson().fromJson(lessonItemsData, object : TypeToken<List<LessonItem>>() {}.type)
        }

        return emptyList()
    }*/

    override fun onItemClick(lessons: LessonItem) {
        val intent1 = when (lessons.lessonName) {
            "Lesson 1" -> Intent(this, Lesson1Activity::class.java)
            else -> null
        }
        intent1?.let {
            startActivity(intent1)
            overridePendingTransition(0, 0)
        }
        if (lessons.lessonName == "Lesson 2" && QUIZ1_PASSED) {
            val intent2 = when (lessons.lessonName) {
                "Lesson 2" -> Intent(this, Lesson2Activity::class.java)
                else -> null
            }
            intent2?.let {
                startActivity(intent2)
            }
        }
        if (lessons.lessonName == "Lesson 3" && QUIZ2_PASSED) {
            val intent3 = when (lessons.lessonName) {
                "Lesson 3" -> Intent(this, Lesson3Activity::class.java)
                else -> null
            }
            intent3?.let {
                startActivity(intent3)
            }
        }
        if (lessons.lessonName == "Lesson 4" && QUIZ3_PASSED) {
            val intent4 = when (lessons.lessonName) {
                "Lesson 4" -> Intent(this, Lesson4Activity::class.java)
                else -> null
            }
            intent4?.let {
                startActivity(intent4)
            }
        }
        if (lessons.lessonName == "Lesson 5" && QUIZ4_PASSED) {
            val intent5 = when (lessons.lessonName) {
                "Lesson 5" -> Intent(this, Lesson5Activity::class.java)
                else -> null
            }
            intent5?.let {
                startActivity(intent5)
            }
        }
        if (lessons.lessonName == "Lesson 6" && QUIZ5_PASSED) {
            val intent6 = when (lessons.lessonName) {
                "Lesson 6" -> Intent(this, Lesson6Activity::class.java)
                else -> null
            }
            intent6?.let {
                startActivity(intent6)
            }
        }
        if (lessons.lessonName == "Lesson 7" && QUIZ6_PASSED) {
            val intent7 = when (lessons.lessonName) {
                "Lesson 7" -> Intent(this, Lesson7Activity::class.java)
                else -> null
            }
            intent7?.let {
                startActivity(intent7)
            }
        }
        if (lessons.lessonName == "Lesson 8" && QUIZ7_PASSED) {
            val intent8 = when (lessons.lessonName) {
                "Lesson 8" -> Intent(this, Lesson8Activity::class.java)
                else -> null
            }
            intent8?.let {
                startActivity(intent8)
            }
        }
        if (lessons.lessonName == "Lesson 9" && QUIZ8_PASSED) {
            val intent9 = when (lessons.lessonName) {
                "Lesson 9" -> Intent(this, Lesson9Activity::class.java)
                else -> null
            }
            intent9?.let {
                startActivity(intent9)
            }
        }
        if (lessons.lessonName == "Lesson 10" && QUIZ9_PASSED) {
            val intent10 = when (lessons.lessonName) {
                "Lesson 10" -> Intent(this, Lesson10Activity::class.java)
                else -> null
            }
            intent10?.let {
                startActivity(intent10)
            }
        }
        if (lessons.lessonName == "Lesson 11" && QUIZ10_PASSED) {
            val intent11 = when (lessons.lessonName) {
                "Lesson 11" -> Intent(this, Lesson11Activity::class.java)
                else -> null
            }
            intent11?.let {
                startActivity(intent11)
            }
        }
        if (lessons.lessonName == "Lesson 12" && QUIZ11_PASSED) {
            val intent12 = when (lessons.lessonName) {
                "Lesson 12" -> Intent(this, Lesson12Activity::class.java)
                else -> null
            }
            intent12?.let {
                startActivity(intent12)
            }
        }
        if (lessons.lessonName == "Lesson 13" && QUIZ12_PASSED) {
            val intent13 = when (lessons.lessonName) {
                "Lesson 13" -> Intent(this, Lesson13Activity::class.java)
                else -> null
            }
            intent13?.let {
                startActivity(intent13)
            }
        }
        if (lessons.lessonName == "Lesson 14" && QUIZ13_PASSED) {
            val intent14 = when (lessons.lessonName) {
                "Lesson 14" -> Intent(this, Lesson14Activity::class.java)
                else -> null
            }
            intent14?.let {
                startActivity(intent14)
            }
        }
        if (lessons.lessonName == "Lesson 15" && QUIZ14_PASSED) {
            val intent15 = when (lessons.lessonName) {
                "Lesson 15" -> Intent(this, Lesson15Activity::class.java)
                else -> null
            }
            intent15?.let {
                startActivity(intent15)
            }
        }
        if (lessons.lessonName == "Lesson 16" && QUIZ15_PASSED) {
            val intent16 = when (lessons.lessonName) {
                "Lesson 16" -> Intent(this, Lesson16Activity::class.java)
                else -> null
            }
            intent16?.let {
                startActivity(intent16)
            }
        }
        if (lessons.lessonName == "Lesson 17" && QUIZ16_PASSED) {
            val intent17 = when (lessons.lessonName) {
                "Lesson 17" -> Intent(this, Lesson17Activity::class.java)
                else -> null
            }
            intent17?.let {
                startActivity(intent17)
            }
        }
        if (lessons.lessonName == "Lesson 18" && QUIZ17_PASSED) {
            val intent18 = when (lessons.lessonName) {
                "Lesson 18" -> Intent(this, Lesson18Activity::class.java)
                else -> null
            }
            intent18?.let {
                startActivity(intent18)
            }
        }
        if (lessons.lessonName == "Lesson 19" && QUIZ18_PASSED) {
            val intent19 = when (lessons.lessonName) {
                "Lesson 19" -> Intent(this, Lesson19Activity::class.java)
                else -> null
            }
            intent19?.let {
                startActivity(intent19)
            }
        }

    /*override fun onItemClick(data: LessonItem) {
        val intent1 = when (data.lesson) {
            "Lesson 1" -> Intent(this, Lesson1Activity::class.java)
            else -> null
        }
        intent1?.let {
            startActivity(intent1)
            overridePendingTransition(0, 0)
        }
        if (data.lesson == "Lesson 2" && QUIZ1_PASSED) {
            val intent2 = when (data.lesson) {
                "Lesson 2" -> Intent(this, Lesson2Activity::class.java)
                else -> null
            }
            intent2?.let {
                startActivity(intent2)
            }
        }
        if (data.lesson == "Lesson 3" && QUIZ2_PASSED) {
            val intent3 = when (data.lesson) {
                "Lesson 3" -> Intent(this, Lesson3Activity::class.java)
                else -> null
            }
            intent3?.let {
                startActivity(intent3)
            }
        }
        if (data.lesson == "Lesson 4" && QUIZ3_PASSED) {
            val intent4 = when (data.lesson) {
                "Lesson 4" -> Intent(this, Lesson4Activity::class.java)
                else -> null
            }
            intent4?.let {
                startActivity(intent4)
            }
        }
        if (data.lesson == "Lesson 5" && QUIZ4_PASSED) {
            val intent5 = when (data.lesson) {
                "Lesson 5" -> Intent(this, Lesson5Activity::class.java)
                else -> null
            }
            intent5?.let {
                startActivity(intent5)
            }
        }
        if (data.lesson == "Lesson 6" && QUIZ5_PASSED) {
            val intent6 = when (data.lesson) {
                "Lesson 6" -> Intent(this, Lesson6Activity::class.java)
                else -> null
            }
            intent6?.let {
                startActivity(intent6)
            }
        }
        if (data.lesson == "Lesson 7" && QUIZ6_PASSED) {
            val intent7 = when (data.lesson) {
                "Lesson 7" -> Intent(this, Lesson7Activity::class.java)
                else -> null
            }
            intent7?.let {
                startActivity(intent7)
            }
        }
        if (data.lesson == "Lesson 8" && QUIZ7_PASSED) {
            val intent8 = when (data.lesson) {
                "Lesson 8" -> Intent(this, Lesson8Activity::class.java)
                else -> null
            }
            intent8?.let {
                startActivity(intent8)
            }
        }
        if (data.lesson == "Lesson 9" && QUIZ8_PASSED) {
            val intent9 = when (data.lesson) {
                "Lesson 9" -> Intent(this, Lesson9Activity::class.java)
                else -> null
            }
            intent9?.let {
                startActivity(intent9)
            }
        }
        if (data.lesson == "Lesson 10" && QUIZ9_PASSED) {
            val intent10 = when (data.lesson) {
                "Lesson 10" -> Intent(this, Lesson10Activity::class.java)
                else -> null
            }
            intent10?.let {
                startActivity(intent10)
            }
        }
        if (data.lesson == "Lesson 11" && QUIZ10_PASSED) {
            val intent11 = when (data.lesson) {
                "Lesson 11" -> Intent(this, Lesson11Activity::class.java)
                else -> null
            }
            intent11?.let {
                startActivity(intent11)
            }
        }
        if (data.lesson == "Lesson 12" && QUIZ11_PASSED) {
            val intent12 = when (data.lesson) {
                "Lesson 12" -> Intent(this, Lesson12Activity::class.java)
                else -> null
            }
            intent12?.let {
                startActivity(intent12)
            }
        }
        if (data.lesson == "Lesson 13" && QUIZ12_PASSED) {
            val intent13 = when (data.lesson) {
                "Lesson 13" -> Intent(this, Lesson13Activity::class.java)
                else -> null
            }
            intent13?.let {
                startActivity(intent13)
            }
        }
        if (data.lesson == "Lesson 14" && QUIZ13_PASSED) {
            val intent14 = when (data.lesson) {
                "Lesson 14" -> Intent(this, Lesson14Activity::class.java)
                else -> null
            }
            intent14?.let {
                startActivity(intent14)
            }
        }
        if (data.lesson == "Lesson 15" && QUIZ14_PASSED) {
            val intent15 = when (data.lesson) {
                "Lesson 15" -> Intent(this, Lesson15Activity::class.java)
                else -> null
            }
            intent15?.let {
                startActivity(intent15)
            }
        }
        if (data.lesson == "Lesson 16" && QUIZ15_PASSED) {
            val intent16 = when (data.lesson) {
                "Lesson 16" -> Intent(this, Lesson16Activity::class.java)
                else -> null
            }
            intent16?.let {
                startActivity(intent16)
            }
        }
        if (data.lesson == "Lesson 17" && QUIZ16_PASSED) {
            val intent17 = when (data.lesson) {
                "Lesson 17" -> Intent(this, Lesson17Activity::class.java)
                else -> null
            }
            intent17?.let {
                startActivity(intent17)
            }
        }
        if (data.lesson == "Lesson 18" && QUIZ17_PASSED) {
            val intent18 = when (data.lesson) {
                "Lesson 18" -> Intent(this, Lesson18Activity::class.java)
                else -> null
            }
            intent18?.let {
                startActivity(intent18)
            }
        }
        if (data.lesson == "Lesson 19" && QUIZ18_PASSED) {
            val intent19 = when (data.lesson) {
                "Lesson 19" -> Intent(this, Lesson19Activity::class.java)
                else -> null
            }
            intent19?.let {
                startActivity(intent19)
            }
        }*/
    }

    @SuppressLint("SetTextI18n")
    private fun setProgressBar() {
        var completedLessons = 0
        if (QUIZ1_PASSED) completedLessons++
        if (QUIZ2_PASSED) completedLessons++
        if (QUIZ3_PASSED) completedLessons++
        if (QUIZ4_PASSED) completedLessons++
        if (QUIZ5_PASSED) completedLessons++
        if (QUIZ6_PASSED) completedLessons++
        if (QUIZ7_PASSED) completedLessons++
        if (QUIZ8_PASSED) completedLessons++
        if (QUIZ9_PASSED) completedLessons++
        if (QUIZ10_PASSED) completedLessons++
        if (QUIZ11_PASSED) completedLessons++
        if (QUIZ12_PASSED) completedLessons++
        if (QUIZ13_PASSED) completedLessons++
        if (QUIZ14_PASSED) completedLessons++
        if (QUIZ15_PASSED) completedLessons++
        if (QUIZ16_PASSED) completedLessons++
        if (QUIZ17_PASSED) completedLessons++
        if (QUIZ19_PASSED) completedLessons++
        if (QUIZ19_PASSED) completedLessons++

        binding.progressBar.progress = completedLessons

        val scorePercentage = (completedLessons.toFloat() / mMaxLessons) * 100
        val formattedPercentage = String.format("%.0f", scorePercentage)
        binding.tvProgress.text = "$formattedPercentage%"

        binding.tvProgressStatus.text = "$completedLessons out of $mMaxLessons lessons\nis complete."
    }

    private fun showTipsDialog(tips: Tips) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.activity_tip_of_the_day)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnClose = dialog.findViewById<ImageButton>(R.id.btn_close_tips)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        val titleTextView = dialog.findViewById<TextView>(R.id.tv_title)
        titleTextView.text = tips.title

        val tipsTextView = dialog.findViewById<TextView>(R.id.tv_tips)
        tipsTextView.text = tips.tips

        dialog.show()
    }

    /*private fun updateRecyclerView(query: String) {
        val filteredList = lessonItemList.filter { lessonItem ->
            lessonItem.title.contains(query, ignoreCase = true) ||
                    lessonItem.lessonName.contains(query, ignoreCase = true)
        }
        recyclerAdapter.updateData(filteredList)
    }*/


    private val imageListener = ImageListener { position, imageView ->
        val imagePosition = position % carouselImages.size
        imageView.setImageResource(carouselImages[imagePosition])
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_main, menu)

        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)
        searchView?.queryHint = "Search title or lessons"

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    /*override fun onQueryTextChange(newText: String): Boolean {
        recyclerAdapter.filter(newText)
        isSearching = newText.isNotEmpty()
        updateCarouselVisibility()
        return true
    }*/

    override fun onQueryTextChange(newText: String): Boolean {
        //updateRecyclerView(newText)
        recyclerAdapter.filter(newText)
        isSearching = newText.isNotEmpty()
        updateCarouselVisibility()
        return true
    }

    /*@SuppressLint("SetTextI18n")
    private fun updateCarouselVisibility() {
        if (isSearching) {
            binding.tvContents.visibility = View.GONE
            binding.tvLessons.visibility = View.GONE
            if (recyclerAdapter.itemCount == 0) {
                binding.tvLessons.text = "No results found"
            }
        } else {
            binding.tvContents.visibility = View.VISIBLE
            binding.tvLessons.visibility = View.VISIBLE
            binding.tvLessons.text = "Lessons"
        }
    }*/

    @SuppressLint("SetTextI18n")
    private fun updateCarouselVisibility() {
        if (isSearching) {
            binding.tvContents.visibility = View.GONE
            binding.tvLessons.visibility = View.INVISIBLE
            if (recyclerAdapter.itemCount == 0) {
                binding.tvLessons.text = "No results found"
            }
        } else {
            binding.tvContents.visibility = View.VISIBLE
            binding.tvLessons.visibility = View.VISIBLE
            binding.tvLessons.text = "Lessons"
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (isSearching) {
            if (searchView.query.isBlank()) {
                isSearching = false
                updateCarouselVisibility()
            }
            recyclerAdapter.filter("")
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> {
                val intent = Intent(applicationContext, AboutActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent)
                overridePendingTransition(0, 0)
            }
            R.id.search -> {
                isSearching = true
                updateCarouselVisibility()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        var QUIZ1_PASSED: Boolean = false
        var QUIZ2_PASSED: Boolean = false
        var QUIZ3_PASSED: Boolean = false
        var QUIZ4_PASSED: Boolean = false
        var QUIZ5_PASSED: Boolean = false
        var QUIZ6_PASSED: Boolean = false
        var QUIZ7_PASSED: Boolean = false
        var QUIZ8_PASSED: Boolean = false
        var QUIZ9_PASSED: Boolean = false
        var QUIZ10_PASSED: Boolean = false
        var QUIZ11_PASSED: Boolean = false
        var QUIZ12_PASSED: Boolean = false
        var QUIZ13_PASSED: Boolean = false
        var QUIZ14_PASSED: Boolean = false
        var QUIZ15_PASSED: Boolean = false
        var QUIZ16_PASSED: Boolean = false
        var QUIZ17_PASSED: Boolean = false
        var QUIZ18_PASSED: Boolean = false
        var QUIZ19_PASSED: Boolean = false

        const val IN_PROGRESS = R.drawable.ic_unlock
        const val COMPLETED = R.drawable.ic_done
        const val LOCK = R.drawable.ic_lock

    }
}
