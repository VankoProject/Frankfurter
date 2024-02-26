package com.kliachenko.frankfurter.core.modules

import com.kliachenko.frankfurter.core.Core
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.main.MainViewModel

class MainModule(
    private val core: Core,
    private val clear: Clear,
) : Module<MainViewModel> {
    override fun viewModel(): MainViewModel {
        return MainViewModel(navigation = core.provideNavigation(), clear)
    }
}