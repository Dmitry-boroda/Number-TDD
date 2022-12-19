package com.example.number.main.sl

import com.example.number.main.presentation.MainViewModel

class MainModule(private val provideNavigation: ProvideNavigation) : Module<MainViewModel> {
    override fun viewModel(): MainViewModel {
        return MainViewModel(
            provideNavigation.provideNavigation()
        )
    }
}