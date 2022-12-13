package com.example.number.main.sl

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.number.BuildConfig

class NumbersApp : Application(), ProvideViewModel {
    private lateinit var viewModelsFactory: ViewModelsFactory
    override fun onCreate() {
        super.onCreate()
        viewModelsFactory =
            ViewModelsFactory(
                DependencyContainer.Base(
                    Core.Base(
                        this,
                        if (BuildConfig.DEBUG) ProvideInstances.Mock(this)
                        else ProvideInstances.Realise(this)
                    )
                )
            )
    }

    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T =
        ViewModelProvider(owner, viewModelsFactory)[clazz]
}