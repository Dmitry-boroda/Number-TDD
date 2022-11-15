package com.example.number.numbers.domain

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NumbersInteractorTest {

    private lateinit var interactor: NumbersInteractor

    private lateinit var repository: TestNumbersRepository

    @Before
    fun setUp() {
        repository = TestNumbersRepository()
        interactor = NumbersInteractor.Base(repository)
    }

    @Test
    fun test_init_success() = runBlocking {
        repository.changeExpectedList(listOf(NumberFact("6", "fact about 6")))
        val actual = interactor.init()
        val expected = NumbersResult.Success(listOf(NumberFact("6", "fact about 6")))
        assertEquals(expected, actual)
        assertEquals(1, repository.allNumbersCallCount)
    }

    @Test
    fun test_about_fact_success(): Unit = runBlocking {
        repository.changeExpectFactOfNumber(NumberFact("7", "fact about 7"))
        val actual = interactor.factAboutNumber("7")
        val expected = NumbersResult.Success(listOf(NumberFact("7", "fact about 7")))

        assertEquals(expected, actual)
        assertEquals("7", repository.numbersFactCalledList[0])
        assertEquals(1, repository.numbersFactCalledList.size)
    }

    @Test
    fun test_about_fact_error(): Unit = runBlocking {
        repository.expectingErrorGetFact(true)
        val actual = interactor.factAboutNumber("7")
        val expected = NumbersResult.Failure("No internet connection")
        assertEquals(expected, actual)
        assertEquals("7", repository.numbersFactCalledList[0])
        assertEquals(1, repository.numbersFactCalledList.size)

    }

    @Test
    fun test_about_random_fact_success(): Unit = runBlocking {
        repository.changeExpectFactOfRandomNumber(NumberFact("7", "fact about 7"))
        val actual = interactor.factAboutRandomNumber()
        val expected = NumbersResult.Success(listOf(NumberFact("7", "fact about 7")))

        assertEquals(expected, actual)
        assertEquals(1, repository.randomNumbersFactCalledList.size)
    }

    @Test
    fun test_about_random_fact_error(): Unit = runBlocking {
        repository.expectingErrorGetRandomFact(true)
        val actual = interactor.factAboutRandomNumber()
        val expected = NumbersResult.Failure("No internet connection")
        assertEquals(expected, actual)
        assertEquals(1, repository.numbersFactCalledList.size)

    }

    private class TestNumbersRepository : NumbersRepository {

        private val allNumbers = mutableListOf<NumberFact>()
        private var numberFact = NumberFact("", "")
        private var errorWhileNumberFact = false

        var allNumbersCallCount = 0
        var numbersFactCalledList = mutableListOf<String>()
        var randomNumbersFactCalledList = mutableListOf<String>()

        fun changeExpectedList(list: List<NumberFact>) {
            allNumbers.clear()
            allNumbers.addAll(list)
        }

        fun changeExpectFactOfNumber(numberFact: NumberFact) {
            this.numberFact = numberFact
        }

        fun changeExpectFactOfRandomNumber(numberFact: NumberFact) {
            this.numberFact = numberFact
        }

        fun expectingErrorGetFact(error: Boolean) {
            errorWhileNumberFact = error
        }

        fun expectingErrorGetRandomFact(error: Boolean) {
            errorWhileNumberFact = error
        }

        override fun allNumbers(): List<NumberFact> {
            allNumbersCallCount++
            return allNumbers
        }

        override fun numberFact(numer: String): NumberFact {

            numbersFactCalledList.add(numer)
            if (errorWhileNumberFact)
                throw NoInternetConnectException()
            return numberFact
        }

        override fun randomNumberFact(): NumberFact {

            randomNumbersFactCalledList.add("")
            if (errorWhileNumberFact)
                throw NoInternetConnectException()
            return numberFact
        }

    }
}