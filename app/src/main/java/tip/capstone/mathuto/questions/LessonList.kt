package tip.capstone.mathuto.questions

import tip.capstone.mathuto.MainActivity.Companion.COMPLETED
import tip.capstone.mathuto.MainActivity.Companion.IN_PROGRESS
import tip.capstone.mathuto.MainActivity.Companion.LOCK
import tip.capstone.mathuto.MainActivity.Companion.QUIZ10_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ11_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ12_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ13_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ14_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ15_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ16_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ17_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ18_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ19_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ1_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ2_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ3_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ4_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ5_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ6_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ7_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ8_PASSED
import tip.capstone.mathuto.MainActivity.Companion.QUIZ9_PASSED
import tip.capstone.mathuto.R
import tip.capstone.mathuto.sqlite.LessonItem
import tip.capstone.mathuto.sqlite.SQLiteHelper
import java.io.Serializable

object LessonList : Serializable {

    fun getLessons(dbHelper: SQLiteHelper): ArrayList<LessonItem> {
        val lessonList = ArrayList<LessonItem>()
        val lessonsFromDb = dbHelper.getAllLessonItems()

        val lesson1 = LessonItem(
            id = 1,
            imageResId = R.drawable.recycler_img_1,
            title = "Addition and Subtraction of Fractions",
            lessonName = "Lesson 1",
            status = if (QUIZ1_PASSED) COMPLETED else IN_PROGRESS
        )
        lessonList.add(lesson1)

        val lesson2 = LessonItem(
            id = 2,
            imageResId = R.drawable.recycler_img_2,
            title = "Problem Solving Involving Addition and Subtraction of Fractions",
            lessonName = "Lesson 2",
            status = if (QUIZ2_PASSED) COMPLETED else if (QUIZ1_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson2)

        val lesson3 = LessonItem(
            id = 3,
            imageResId = R.drawable.recycler_img_3,
            title = "Multiplication of Simple Fractions and Mixed Fractions",
            lessonName = "Lesson 3",
            status = if (QUIZ3_PASSED) COMPLETED else if (QUIZ2_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson3)

        val lesson4 = LessonItem(
            id = 4,
            imageResId = R.drawable.recycler_img_4,
            title = "Problem Solving on Multiplication of Fractions",
            lessonName = "Lesson 4",
            status = if (QUIZ4_PASSED) COMPLETED else if (QUIZ3_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson4)

        val lesson5 = LessonItem(
            id = 5,
            imageResId = R.drawable.recycler_img_5,
            title = "Dividing Simple Fractions and Mixed Fractions",
            lessonName = "Lesson 5",
            status = if (QUIZ5_PASSED) COMPLETED else if (QUIZ4_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson5)

        val lesson6 = LessonItem(
            id = 6,
            imageResId = R.drawable.recycler_img_1,
            title = "Solving Routine or Non-Routine Problems Involving Division Without or With Any of the Other Operations of Fractions and Mixed Fractions",
            lessonName = "Lesson 6",
            status = if (QUIZ6_PASSED) COMPLETED else if (QUIZ5_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson6)

        val lesson7 = LessonItem(
            id = 7,
            imageResId = R.drawable.recycler_img_2,
            title = "Addition and Subtraction of Decimals",
            lessonName = "Lesson 7",
            status = if (QUIZ7_PASSED) COMPLETED else if (QUIZ6_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson7)

        val lesson8 = LessonItem(
            id = 8,
            imageResId = R.drawable.recycler_img_3,
            title = "Solving Routine or Non-Routine Problems Involving Addition and Subtraction of Decimals and Mixed Decimals Using Appropriate Problem Solving Strategies and Tools",
            lessonName = "Lesson 8",
            status = if (QUIZ8_PASSED) COMPLETED else if (QUIZ7_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson8)

        val lesson9 = LessonItem(
            id = 9,
            imageResId = R.drawable.recycler_img_4,
            title = "Multiplication of Decimals and Mixed Decimals",
            lessonName = "Lesson 9",
            status = if (QUIZ9_PASSED) COMPLETED else if (QUIZ8_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson9)

        val lesson10 = LessonItem(
            id = 10,
            imageResId = R.drawable.recycler_img_5,
            title = "Multiplication of Decimals and Mixed Decimals Mentally by 0.1, 0.01, 10 and 100",
            lessonName = "Lesson 10",
            status = if (QUIZ10_PASSED) COMPLETED else if (QUIZ9_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson10)

        val lesson11 = LessonItem(
            id = 11,
            imageResId = R.drawable.recycler_img_1,
            title = "Multiplication of Decimals and Mixed Decimals Mentally by 0.1, 0.01, 10 and 100",
            lessonName = "Lesson 11",
            status = if (QUIZ11_PASSED) COMPLETED else if (QUIZ10_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson11)

        val lesson12 = LessonItem(
            id = 12,
            imageResId = R.drawable.recycler_img_2,
            title = "Multi-Step Problems Involving Multiplication and Addition or Subtraction of Decimals",
            lessonName = "Lesson 12",
            status = if (QUIZ12_PASSED) COMPLETED else if (QUIZ11_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson12)

        val lesson13 = LessonItem(
            id = 13,
            imageResId = R.drawable.recycler_img_3,
            title = "Dividing Whole Numbers by Decimals Up 2 Decimal Places",
            lessonName = "Lesson 13",
            status = if (QUIZ13_PASSED) COMPLETED else if (QUIZ12_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson13)

        val lesson14 = LessonItem(
            id = 14,
            imageResId = R.drawable.recycler_img_4,
            title = "Dividing Decimals and Mixed Decimals",
            lessonName = "Lesson 14",
            status = if (QUIZ14_PASSED) COMPLETED else if (QUIZ13_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson14)

        val lesson15 = LessonItem(
            id = 15,
            imageResId = R.drawable.recycler_img_5,
            title = "Dividing Decimals Up to 4 Decimal Places by 0.1, 0.01 and 0.001",
            lessonName = "Lesson 15",
            status = if (QUIZ15_PASSED) COMPLETED else if (QUIZ14_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson15)

        val lesson16 = LessonItem(
            id = 16,
            imageResId = R.drawable.recycler_img_1,
            title = "Dividing Decimals Up to 2 Decimal Places by 10, 100 and 1000 Mentally",
            lessonName = "Lesson 16",
            status = if (QUIZ16_PASSED) COMPLETED else if (QUIZ15_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson16)

        val lesson17 = LessonItem(
            id = 17,
            imageResId = R.drawable.recycler_img_2,
            title = "Differentiating Repeating from Terminating and Non-Terminating Decimal Quotient",
            lessonName = "Lesson 17",
            status = if (QUIZ17_PASSED) COMPLETED else if (QUIZ16_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson17)

        val lesson18 = LessonItem(
            id = 18,
            imageResId = R.drawable.recycler_img_3,
            title = "Solving Word Problems Involving Division of Decimals",
            lessonName = "Lesson 18",
            status = if (QUIZ18_PASSED) COMPLETED else if (QUIZ17_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson18)

        val lesson19 = LessonItem(
            id = 19,
            imageResId = R.drawable.recycler_img_4,
            title = "Solving Multi-Step Problems Involving Division of Decimals and Any of the Other Operations",
            lessonName = "Lesson 19",
            status = if (QUIZ19_PASSED) COMPLETED else if (QUIZ18_PASSED) IN_PROGRESS else LOCK
        )
        lessonList.add(lesson19)

        lessonList.addAll(lessonsFromDb)
        return lessonList
    }
}
