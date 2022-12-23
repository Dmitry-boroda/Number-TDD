package com.example.number.main.sl

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.number.BuildConfig
import com.example.number.numbers.domain.RandomNumberRepository
import com.example.number.random.ProvidePeriodicRepository

class NumbersApp : Application(), ProvideViewModel, ProvidePeriodicRepository {
    private lateinit var viewModelsFactory: ViewModelsFactory
    private lateinit var dependencyContainer: DependencyContainer.Base
    override fun onCreate() {
        super.onCreate()

        val provideInstances = if (
            BuildConfig.DEBUG
        )
            ProvideInstances.Mock(this)
        else
            ProvideInstances.Realise(this)

        dependencyContainer = DependencyContainer.Base(Core.Base(this, provideInstances))
        viewModelsFactory = ViewModelsFactory(dependencyContainer)
    }

    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T =
        ViewModelProvider(owner, viewModelsFactory)[clazz]

    override fun providePeriodicRepository(): RandomNumberRepository {
        return dependencyContainer.provideRepository()
    }
}