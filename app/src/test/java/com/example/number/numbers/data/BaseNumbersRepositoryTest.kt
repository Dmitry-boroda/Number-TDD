package com.example.number.numbers.data

import com.example.number.numbers.domain.NumbersRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BaseNumbersRepositoryTest {

    private lateinit var repository: NumbersRepository
    private lateinit var cloudDataSource: NumbersCloudDataSource
    private lateinit var cacheDataSource: NumbersCacheDataSource

    @Before
    fun setUp(){

        cloudDataSource = TestNumbersCloudDataSource()
        cacheDataSource = TestNumbersCacheDataSource()
        repository = BaseNumbersRepository(cloudDataSource,cacheDataSource)
    }

    @Test
    fun test_all_numbers() = runBlocking {

    }

    private class TestNumbersCloudDataSource: NumbersCloudDataSource{

    }
    private class TestNumbersCacheDataSource: NumbersCacheDataSource{

        private val data = mutableListOf<NumberData>()
        fun replaceData(newData: List<NumberData>){
            data.clear()
            data.addAll(newData)
        }
        override fun allNumbers(): List<NumberData>{

        }
    }
}