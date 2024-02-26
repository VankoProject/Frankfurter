package com.kliachenko.frankfurter.core.modules

import com.kliachenko.frankfurter.core.Core
import com.kliachenko.frankfurter.core.ProvideInstance
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.loading.LoadViewModel

class LoadModule(
    private val core: Core,
    private val provideInstance: ProvideInstance,
    private val clear: Clear,
) : Module<LoadViewModel> {
    override fun viewModel(): LoadViewModel {
        return LoadViewModel(
            observable = core.provideObservable(),
            repository = provideInstance.provideRepository(core.provideCacheDataSource()),
            navigation = core.provideNavigation(),
            runAsync = core.provideRunAsync(),
            clear = clear
        )
    }
}