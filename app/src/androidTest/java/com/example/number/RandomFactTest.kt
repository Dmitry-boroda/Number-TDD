package com.example.number

import org.junit.Test

class RandomFactTest : BaseTest() {
    @Test
    fun test() = NumbersPage().run {
        randomFactButton.view().click()
        recycle.run {
            viewInRecycler(0, titleItem).checkText("1")
            viewInRecycler(0, subTitleItem).checkText("fact about 1")
        }

        randomFactButton.view().click()
        recycle.run {
            viewInRecycler(0, titleItem).checkText("2")
            viewInRecycler(0, subTitleItem).checkText("fact about 2")

            viewInRecycler(1, titleItem).checkText("1")
            viewInRecycler(1, subTitleItem).checkText("fact about 1")
        }
    }
}