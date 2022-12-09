package com.example.number.numbers.presentation

import androidx.lifecycle.LifecycleOwner

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.number.R
import com.example.number.numbers.domain.NumbersInteractor

class NumberViewModel(
    private val handelResult: HandelNumbersRequest,
    private val managerResources: ManagerResources,
    private val communications: NumbersCommunications,
    private val interactor: NumbersInteractor,
) : ViewModel(), FetchNumber, ObserveNumbers, ClearError {

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
        communications.observeProgress(owner, observer)

    override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) =
        communications.observeState(owner, observer)

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) =
        communications.observeList(owner, observer)

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            handelResult.handel(viewModelScope) {
                interactor.init()
            }
        }
    }

    override fun fetchRandomNumberFact() {
        handelResult.handel(viewModelScope) {
            interactor.factAboutRandomNumber()
        }

    }

    override fun fetchNumberFact(number: String) {
        if (number.isEmpty()) {
            communications.showState(UiState.ShowError(managerResources.string(R.string.empty_number_error_message)))
        } else {
            handelResult.handel(viewModelScope) {
                interactor.factAboutNumber(number)
            }
        }
    }

    override fun clearError() = communications.showState(UiState.ClearError())
}

interface FetchNumber {
    fun init(isFirstRun: Boolean)
    fun fetchRandomNumberFact()
    fun fetchNumberFact(number: String)
}

interface ClearError {
    fun clearError()
}
