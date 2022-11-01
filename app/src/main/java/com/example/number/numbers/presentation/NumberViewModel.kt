package com.example.number.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.Observer
import com.example.number.numbers.domain.NumberFact
import com.example.number.numbers.domain.NumbersInteractor
import com.example.number.numbers.domain.NumbersResult

class NumberViewModel(
    private val communication: NumbersCommunications,
    private val interactor: NumbersInteractor
    ):FetchNumber,ObserveNumbers {
    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) =
        communication.observeProgress(owner, observer)

    override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) =
        communication.observeState(owner, observer)

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) =
        communication.observeList(owner, observer)

    override fun init(isFirstRun: Boolean) {
        if(isFirstRun){
            communication.showProgress(true)
            viewModelScope.launch{
                val result = interactor.init()
                result.map(object: NumbersResult.Mapper<Unit>{
                    override fun map(list: List<NumberFact>, errorMessage: String) {
                        if (errorMessage.isEmpty())
                    }

                })
            }
        }
    }

    override fun fetchRandomNumberFact() {
        TODO("Not yet implemented")
    }

    override fun fetchNumberFact(number: String) {
        TODO("Not yet implemented")
    }
}
interface FetchNumber{
    fun init(isFirstRun: Boolean)
    fun fetchRandomNumberFact()
    fun fetchNumberFact(number:String)
}