package com.example.number.numbers.presentation

interface NumbersCommunications {
    fun showProgress(show: Boolean)

    fun showState(state: UiState)

    fun showList(list: List<NumberUi>)

    class Base(

    ) {

    }
}

interface ProgressCommunication : Communication.Mutable<Boolean> {
    class Base() : Communication.Post<Boolean>(), ProgressCommunication
}

interface NumbersState : Communication.Mutable<UiState> {
    class Base() : Communication.Post<UiState>(), NumbersState
}

interface NumbersList : Communication.Mutable<List<NumberUi>> {
    class Base() : Communication.Post<List<NumberUi>>(), NumbersList
}
