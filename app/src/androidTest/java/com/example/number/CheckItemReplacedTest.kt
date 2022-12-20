package com.example.number

import org.junit.Test

class CheckItemReplacedTest : BaseTest() {

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