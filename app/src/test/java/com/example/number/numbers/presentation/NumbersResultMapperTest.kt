package com.example.number.numbers.presentation

import com.example.number.numbers.domain.NumberFact
import com.example.number.numbers.domain.NumberUiMapper
import org.junit.Assert.*
import org.junit.Test

class NumbersResultMapperTest : BaseTest() {
    @Test
    fun test_error() {
        val communications = TestNumberCommunication()
        val mapper = NumbersResultMapper(communications, NumberUiMapper())

        mapper.map(emptyList(), "not empty message")

        assertEquals(UiState.ShowError("not empty message"), communications.stateCalledList[0])
    }
    @Test
    fun test_success_no_list(){
        val communications = TestNumberCommunication()
        val mapper = NumbersResultMapper(communications, NumberUiMapper())

        mapper.map(emptyList(),"")

        assertEquals(0,communications.timesShowList)
        assertEquals(true, communications.stateCalledList[0] is UiState.Success)
    }
    @Test
    fun test_success_list(){
        val communications = TestNumberCommunication()
        val mapper = NumbersResultMapper(communications, NumberUiMapper())

        mapper.map(listOf(NumberFact("5","fact 5")),"")

        assertEquals(true, communications.stateCalledList[0] is UiState.Success)
        assertEquals(1,communications.timesShowList)
        assertEquals(NumberUi("5","fact 5"),communications.numbersList[0])
    }

}