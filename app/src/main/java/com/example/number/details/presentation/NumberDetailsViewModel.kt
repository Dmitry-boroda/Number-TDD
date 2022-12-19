package com.example.number.details.presentation

import androidx.lifecycle.ViewModel
import com.example.number.details.data.NumberFactDetails

class NumberDetailsViewModel(
    private val data: NumberFactDetails.Read
) : ViewModel(), NumberFactDetails.Read {
    override fun read(): String = data.read()
}