package com.example.number.main.presentation

import com.example.number.numbers.presentation.Communication

interface NavigationCommunication {
    interface Observe : Communication.Observe<NavigationStrategy>
    interface Mutate : Communication.Mutate<NavigationStrategy>
    interface Mutable : Observe, Mutate
    class Bass : Communication.SingleUi<NavigationStrategy>(), Mutable
}