package com.example.number.numbers.data

import com.example.number.numbers.domain.NumberFact

class NumberDataToDomain: NumberData.Mapper<NumberFact> {
    override fun map(id: String, fact: String) = NumberFact(id,fact)
}