package com.example.number.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.number.numbers.domain.NumbersInteractor

class NumberViewModel(
    private val communication: NumbersCommunications,
    private val interactor: NumbersInteractor
    ):ObserveNumbers {
    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) =
        communication.observeProgress(owner, observer)

    override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) =
        communication.observeState(owner, observer)

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) =
        communication.observeList(owner, observer)
}