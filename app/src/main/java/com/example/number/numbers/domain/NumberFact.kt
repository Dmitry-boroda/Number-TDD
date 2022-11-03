package com.example.number.numbers.domain

import com.example.number.numbers.presentation.NumberUi

data class NumberFact(
    private val id:String,
    private val fact:String
    ){

    interface Mapper<T>{
        fun map (id: String, fact:String):T
    }
    fun <T> map(mapper: Mapper<T>):T = mapper.map(id, fact)
    fun toUi()= NumberUi(id,fact)
}

