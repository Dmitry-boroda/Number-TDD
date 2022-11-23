package com.example.number.numbers.data

import com.example.number.numbers.domain.NoInternetConnectException
import com.example.number.numbers.domain.NumberFact
import com.example.number.numbers.domain.NumbersRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class BaseNumbersRepositoryTest {

    private lateinit var repository: NumbersRepository
    private lateinit var cloudDataSource: TestNumbersCloudDataSource
    private lateinit var cacheDataSource: TestNumbersCacheDataSource

    @Before
    fun setUp() {

        cloudDataSource = TestNumbersCloudDataSource()
        cacheDataSource = TestNumbersCacheDataSource()
        repository = BaseNumbersRepository(cloudDataSource, cacheDataSource)
    }

    @Test
    fun test_all_numbers() = runBlocking {
        cacheDataSource.replaceData(
            listOf(
                NumberData("4", "fact of 4"),
                NumberData("5", "fact of 5"),
            )
        )
        val action = repository.allNumbers()
        val expected = listOf(
            NumberFact("4", "fact of 4"),
            NumberFact("5", "fact of 5"),
        )
        action.forEachIndexed { index, item ->
            assertEquals(expected[index], item)
        }
        assertEquals(1, cacheDataSource.allNumbersCalledCount)
    }

    @Test
    fun test_number_fact_not_cached_success() = runBlocking {
        cloudDataSource.makeExpected(NumberData("10", "fact of 10"))
        cacheDataSource.replaceData(emptyList())

        val action = repository.numberFact("10")
        val expected = NumberData("10", "fact of 10")

        assertEquals(expected, action)
        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(1, cloudDataSource.numberFactCalledCount)
        assertEquals(0, cacheDataSource.numberFactCalled.size)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
        assertEquals(expected, cacheDataSource.data[0])

    }

    @Test(expected = NoInternetConnectException::class)
    fun test_number_fact_not_cached_failure() = runBlocking {
        cloudDataSource.changeConnection(false)
        cacheDataSource.replaceData(emptyList())

        repository.numberFact("10")
        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(1, cloudDataSource.numberFactCalledCount)
        assertEquals(0, cacheDataSource.numberFactCalled.size)
        assertEquals(0, cacheDataSource.saveNumberFactCalledCount)
    }

    @Test
    fun test_number_fact_cached() = runBlocking {
        cloudDataSource.changeConnection(true)
        cloudDataSource.makeExpected(NumberData("10", "cloud 10"))
        cacheDataSource.replaceData(listOf(NumberData("10", "fact 10")))

        val actual = repository.numberFact("10")
        val expected = NumberData("10", "fact 10")

        assertEquals(expected, actual)
        assertEquals(true, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(0, cloudDataSource.numberFactCalledCount)
        assertEquals(1, cacheDataSource.numberFactCalled.size)
        assertEquals(cacheDataSource.numberFactCalled[0], actual)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)

    }

    @Test
    fun test_random_number_fact_not_cached_success() = runBlocking {
        cloudDataSource.makeExpected(NumberData("10", "fact of 10"))
        cacheDataSource.replaceData(emptyList())

        val action = repository.randomNumberFact()
        val expected = NumberData("10", "fact of 10")

        assertEquals(expected, action)
        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(0, cloudDataSource.numberFactCalledCount)
        assertEquals(1, cloudDataSource.randomNumberFactCalledCount)
        assertEquals(0, cacheDataSource.numberFactCalled.size)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
        assertEquals(expected, cacheDataSource.data[0])
    }

    @Test(expected = NoInternetConnectException::class)
    fun test_random_number_fact_not_cached_failure() = runBlocking {
        cloudDataSource.changeConnection(false)
        cacheDataSource.replaceData(emptyList())

        repository.randomNumberFact()
        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(0, cloudDataSource.numberFactCalledCount)
        assertEquals(1, cloudDataSource.randomNumberFactCalledCount)
        assertEquals(0, cacheDataSource.numberFactCalled.size)
        assertEquals(0, cacheDataSource.saveNumberFactCalledCount)
    }

    @Test
    fun test_random_number_fact_cached() = runBlocking {
        cloudDataSource.changeConnection(true)
        cloudDataSource.makeExpected(NumberData("10", "cloud 10"))
        cacheDataSource.replaceData(listOf(NumberData("10", "fact 10")))

        val actual = repository.randomNumberFact()
        val expected = NumberData("10", "clout 10")

        assertEquals(expected, actual)
        assertEquals(1, cloudDataSource.randomNumberFactCalledCount)

        assertEquals(true, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)

        assertEquals(0, cacheDataSource.numberFactCalled.size)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
    }


    private class TestNumbersCloudDataSource : NumbersCloudDataSource {

        private var thereIsConnection = true
        private var numberData = NumberData("", "")
        var numberFactCalledCount = 0
        var randomNumberFactCalledCount = 0

        fun changeConnection(connected: Boolean) {
            thereIsConnection = connected
        }

        fun makeExpected(number: NumberData) {

            numberData = number
        }

        override suspend fun numberFact(number: String): NumberData {
            numberFactCalledCount++
            return if (thereIsConnection) {
                numberData
            } else
                throw UnknownHostException()
        }

        override suspend fun randomNumberFact(): NumberData {
            randomNumberFactCalledCount++
            return if (thereIsConnection) {
                numberData
            } else
                throw UnknownHostException()
        }
    }

    private class TestNumbersCacheDataSource : NumbersCacheDataSource {


        var numberFactCalled = mutableListOf<String>()
        var allNumbersCalledCount = 0
        var saveNumberFactCalledCount = 0

        val containsCalledList = mutableListOf<Boolean>()
        val data = mutableListOf<NumberData>()
        fun replaceData(newData: List<NumberData>): Unit = with(data) {
            clear()
            addAll(newData)
        }

        override suspend fun allNumbers(): List<NumberData> {
            allNumbersCalledCount++
            return data
        }

        override fun contains(number: String): Boolean {

            val result = data.find { it.matches(number) } != null
            containsCalledList.add(result)
            return result
        }

        override suspend fun numberFact(number: String): NumberData {
            numberFactCalled.add(number)
            return data[0]
        }

        override suspend fun saveNumberFact(numberData: NumberData) {
            saveNumberFactCalledCount++
            data.add(numberData)
        }
    }
}