package com.example.number.main.sl

import com.example.number.main.presentation.MainViewModel

class MainModule(private val core: Core) : Module<MainViewModel> {
    override fun viewModel(): MainViewModel {
        return MainViewModel(
            core.provideWorkManagerWrapper(),
            core.provideNavigation()
        )
    }
}