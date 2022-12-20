package com.example.number

import androidx.test.espresso.action.ViewActions.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.number.main.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest : BaseTest() {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun details_navigation() {

        val numbersPage = NumbersPage()
        numbersPage.run {
            input.view().typeText("10")
            getFactButton.view().click()
            recycle.run {
                viewInRecycler(0, titleItem).checkText("10")
                viewInRecycler(0, subTitleItem).checkText("fact about 10")
                viewInRecycler(0, subTitleItem).click()
            }
        }

        val detailsPage = DetailsPage()
        detailsPage.details.view().checkText("10\n\nfact about 10")

        pressBack()
        numbersPage.run {
            recycle.run {
                viewInRecycler(0, titleItem).checkText("10")
                viewInRecycler(0, subTitleItem).checkText("fact about 10")
            }
        }
    }
}