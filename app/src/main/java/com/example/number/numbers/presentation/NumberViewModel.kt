package com.example.number.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.Observer
import com.example.number.numbers.domain.NumbersInteractor
import com.example.number.numbers.domain.NumbersResult

class NumberViewModel(
    private val communications: NumbersCommunications,
    private val interactor: NumbersInteractor,
    private val numbersResultMapper: NumbersResult.Mapper<Unit>
) : FetchNumber, ObserveNumbers {

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) =
        communications.observeProgress(owner, observer)

    override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) =
        communications.observeState(owner, observer)

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) =
        communications.observeList(owner, observer)

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            communications.showProgress(true)
            //viewModelScope.launch {
               // val result = interactor.init()
                //communications.showProgress(false)
                //result.map(numbersResultMapper)
            //}
        }
    }

    override fun fetchRandomNumberFact() {

    }

    override fun fetchNumberFact(number: String) {
        TODO("Not yet implemented")
    }
}

interface FetchNumber {
    fun init(isFirstRun: Boolean)
    fun fetchRandomNumberFact()
    fun fetchNumberFact(number: String)
}