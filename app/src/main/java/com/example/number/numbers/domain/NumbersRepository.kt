package com.example.number.numbers.domain

interface NumbersRepository : RandomNumberRepository {

    suspend fun allNumbers(): List<NumberFact>
    suspend fun numberFact(number: String): NumberFact

}

interface RandomNumberRepository {
    suspend fun randomNumberFact(): NumberFact
}