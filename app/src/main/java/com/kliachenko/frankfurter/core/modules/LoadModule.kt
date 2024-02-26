package com.kliachenko.frankfurter.core.modules

import com.kliachenko.frankfurter.core.Core
import com.kliachenko.frankfurter.core.ProvideInstance
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.loading.BaseLoadResultMapper
import com.kliachenko.presentation.loading.LoadUiObservable
import com.kliachenko.presentation.loading.LoadViewModel

class LoadModule(
    private val core: Core,
    private val provideInstance: ProvideInstance,
    private val clear: Clear,
) : Module<LoadViewModel> {

    override fun viewModel(): LoadViewModel {
        val observable = LoadUiObservable.Base()
        return LoadViewModel(
            observable = observable,
            repository = provideInstance.provideRepository(core.provideCacheDataSource()),
            runAsync = core.provideRunAsync(),
            mapper = BaseLoadResultMapper(observable, core.provideNavigation(), clear),
        )
    }
}