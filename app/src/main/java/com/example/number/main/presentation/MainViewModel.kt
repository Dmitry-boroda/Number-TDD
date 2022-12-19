package com.example.number.main.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.number.numbers.presentation.Communication
import com.example.number.numbers.presentation.NumbersFragment

class MainViewModel(
    private val navigationCommunication: NavigationCommunication.Mutable,

    ) : ViewModel(), Init, Communication.Observe<NavigationStrategy> {

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun)
            navigationCommunication.map(NavigationStrategy.Replace(NumbersFragment()))
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<NavigationStrategy>) {
        navigationCommunication.observe(owner, observer)
    }
}