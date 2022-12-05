package com.example.number.numbers.data.cache

import com.example.number.numbers.data.NumberData

class NumberDataToCache : NumberData.Mapper<NumberCache> {
    override fun map(id: String, fact: String) = NumberCache(id, fact, System.currentTimeMillis())
}