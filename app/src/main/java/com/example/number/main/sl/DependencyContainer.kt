package com.example.number.main.sl

import androidx.lifecycle.ViewModel
import com.example.number.details.presentation.NumberDetailsViewModel
import com.example.number.details.sl.NumberDetailsModule
import com.example.number.main.presentation.MainViewModel
import com.example.number.numbers.presentation.NumberViewModel
import com.example.number.numbers.sl.NumbersModule

interface DependencyContainer {

    fun <T : ViewModel> module(clazz: Class<T>): Module<*>
    class Error : DependencyContainer {
        override fun <T : ViewModel> module(clazz: Class<T>): Module<*> {
            throw IllegalStateException("not module found for &clazz")
        }
    }

    class Base(
        private val core: Core,
        private val dependencyContainer: DependencyContainer = Error()
    ) : DependencyContainer {
        override fun <T : ViewModel> module(clazz: Class<T>): Module<*> {
            return when (clazz) {
                NumberViewModel.Base::class.java -> NumbersModule(core)
                MainViewModel::class.java -> MainModule(core)
                NumberDetailsViewModel::class.java -> NumberDetailsModule(core)
                else -> dependencyContainer.module(clazz)
            }
        }
    }
}