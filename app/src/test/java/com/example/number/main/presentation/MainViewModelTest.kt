package com.example.number.main.presentation

import com.example.number.numbers.presentation.BaseTest
import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest : BaseTest() {
    @Test
    fun test_navigation_at_start() {

        val navigation = TestNavigationCommunication()
        val mainViewModel = MainViewModel(navigation)

        mainViewModel.init(true)
        assertEquals(1, navigation.count)
        assertEquals(true, navigation.strategy is NavigationStrategy.Replace)

        mainViewModel.init(false)
        assertEquals(1, navigation.count)
    }
}