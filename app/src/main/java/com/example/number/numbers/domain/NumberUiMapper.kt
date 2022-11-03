package com.example.number.numbers.domain

import com.example.number.numbers.presentation.NumberUi

class NumberUiMapper: NumberFact.Mapper<NumberUi> {
    override fun map(id: String, fact: String): NumberUi {
        return NumberUi(id, fact)
    }
}