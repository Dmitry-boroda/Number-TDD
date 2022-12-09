package com.example.number.numbers.presentation

import android.widget.TextView

data class NumberUi(private val id: String, private val fact: String) : Mapper<Boolean, NumberUi> {

    fun map(head: TextView, subTitle: TextView) {
        head.text = id
        subTitle.text = fact
    }

    override fun map(source: NumberUi) = source.id == id
}