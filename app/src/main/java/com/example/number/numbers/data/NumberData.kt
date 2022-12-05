package com.example.number.numbers.data

data class NumberData(
    private val id: String,
    private val fact: String
) {

    interface Mapper<T> {
        fun map(id: String, fact: String): T

        class MatchesId(private val id: String) : Mapper<Boolean> {
            override fun map(id: String, fact: String) = this.id == id
        }

        class Matches(private val data: NumberData) : Mapper<Boolean> {
            override fun map(id: String, fact: String): Boolean = data.id == id
        }
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, fact)
}