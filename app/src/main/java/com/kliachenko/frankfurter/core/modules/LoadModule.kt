package com.kliachenko.frankfurter.core.modules

import com.kliachenko.data.loading.cache.CurrencyCacheDataSource
import com.kliachenko.data.loading.cloud.LoadCurrencyCloudDataSource
import com.kliachenko.frankfurter.core.Core
import com.kliachenko.frankfurter.core.ProvideInstance
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.loading.BaseLoadResultMapper
import com.kliachenko.presentation.loading.LoadUiObservable
import com.kliachenko.presentation.loading.LoadViewModel

class LoadModule(
    private val core: Core,
    private val provideInstance: ProvideInstance,
    private val clearViewModel: Clear,
) : Module<LoadViewModel> {

    override fun viewModel(): LoadViewModel {
        val observable = LoadUiObservable.Base()
        val cloudDataSource = LoadCurrencyCloudDataSource.Base()
        val currencyCacheDataSource =
            CurrencyCacheDataSource.Base(core.provideCurrencyDataBase().dataBase())
        return LoadViewModel(
            observable = observable,
            repository = provideInstance.provideRepository(
                loadCurrencyCloudDataSource = cloudDataSource,
                provideResources = core.provideResources(),
                currencyCacheDataSource = currencyCacheDataSource
            ),
            runAsync = core.provideRunAsync(),
            mapper = BaseLoadResultMapper(observable, core.provideNavigation(), clearViewModel),
        )
    }
}