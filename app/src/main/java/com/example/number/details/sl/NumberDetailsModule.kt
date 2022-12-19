package com.example.number.details.sl

import com.example.number.details.presentation.NumberDetailsViewModel
import com.example.number.main.sl.Module
import com.example.number.main.sl.ProvideNumberDetails

class NumberDetailsModule(
    private val provideNumberDetails: ProvideNumberDetails
) : Module<NumberDetailsViewModel> {
    override fun viewModel() = NumberDetailsViewModel(provideNumberDetails.provideNumberDetails())
}