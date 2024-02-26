package com.kliachenko.frankfurter.core.modules

import com.kliachenko.frankfurter.core.Core
import com.kliachenko.presentation.main.MainViewModel

class MainModule(
    private val core: Core,
) : Module<MainViewModel> {
    override fun viewModel(): MainViewModel {
        return MainViewModel(navigation = core.provideNavigation())
    }
}