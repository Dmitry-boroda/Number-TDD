package com.example.number.main.sl

import androidx.lifecycle.ViewModel
import com.example.number.details.presentation.NumberDetailsViewModel
import com.example.number.details.sl.NumberDetailsModule
import com.example.number.main.presentation.MainViewModel
import com.example.number.numbers.domain.NumbersRepository
import com.example.number.numbers.presentation.NumberViewModel
import com.example.number.numbers.sl.NumbersModule
import com.example.number.numbers.sl.ProvideNumbersRepository

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
    ) : DependencyContainer, ProvideNumbersRepository {

        private val repository by lazy {
            ProvideNumbersRepository.Base(core).provideRepository()
        }

        override fun <T : ViewModel> module(clazz: Class<T>): Module<*> {
            return when (clazz) {
                NumberViewModel.Base::class.java -> NumbersModule(core, this)
                MainViewModel::class.java -> MainModule(core)
                NumberDetailsViewModel::class.java -> NumberDetailsModule(core)
                else -> dependencyContainer.module(clazz)
            }
        }

        override fun provideRepository(): NumbersRepository = repository
    }
}