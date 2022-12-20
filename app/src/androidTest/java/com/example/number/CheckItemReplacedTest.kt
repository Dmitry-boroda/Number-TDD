package com.example.number

import androidx.test.espresso.Espresso.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.number.main.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CheckItemReplacedTest : BaseTest() {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_not_duplicated_item(): Unit = NumbersPage().run {

        input.view().typeText("10")
        getFactButton.view().click()

        recycle.run {
            viewInRecycler(0, titleItem).checkText("10")
            viewInRecycler(0, subTitleItem).checkText("fact about 10")
        }

        input.view().typeText("11")
        getFactButton.view().click()

        recycle.run {
            viewInRecycler(0, titleItem).checkText("11")
            viewInRecycler(0, subTitleItem).checkText("fact about 11")

            viewInRecycler(1, titleItem).checkText("10")
            viewInRecycler(1, subTitleItem).checkText("fact about 10")
        }

        input.view().typeText("10")
        getFactButton.view().click()

        recycle.run {
            viewInRecycler(0, titleItem).checkText("10")
            viewInRecycler(0, subTitleItem).checkText("fact about 10")

            viewInRecycler(1, titleItem).checkText("11")
            viewInRecycler(1, subTitleItem).checkText("fact about 11")
        }
    }

}