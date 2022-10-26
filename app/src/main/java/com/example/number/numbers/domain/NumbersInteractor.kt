package com.example.number.numbers.domain

interface NumbersInteractor {
    suspend fun init(): NumbersResult
    suspend fun factAboutNumber(number: String): NumbersResult
    suspend fun factAboutRandomNumber(): NumbersResult
}