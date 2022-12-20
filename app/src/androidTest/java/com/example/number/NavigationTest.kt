package com.example.number

import androidx.test.espresso.action.ViewActions.*
import org.junit.Test

class NavigationTest : BaseTest() {

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