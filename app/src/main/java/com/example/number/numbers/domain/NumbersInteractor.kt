package com.example.number.numbers.domain

import com.example.number.details.data.NumberFactDetails

interface NumbersInteractor {
    fun saveDetails(details: String)
    suspend fun init(): NumbersResult
    suspend fun factAboutNumber(number: String): NumbersResult
    suspend fun factAboutRandomNumber(): NumbersResult

    class Base(
        private val repository: NumbersRepository,
        private val handleRequest: HandleRequest,
        private val numbersFactDetails: NumberFactDetails.Save
    ) : NumbersInteractor {
        override fun saveDetails(details: String) = numbersFactDetails.save(details)

        override suspend fun init() = NumbersResult.Success(repository.allNumbers())

        override suspend fun factAboutNumber(number: String) = handleRequest.handle {
            repository.numberFact(number)
        }

        override suspend fun factAboutRandomNumber() = handleRequest.handle {
            repository.randomNumberFact()
        }
    }
}

