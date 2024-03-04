package com.kliachenko.frankfurter.core.module

import com.kliachenko.data.loading.cache.CurrencyCacheDataSource
import com.kliachenko.data.loading.cloud.CurrencyService
import com.kliachenko.data.loading.cloud.LoadCurrencyCloudDataSource
import com.kliachenko.frankfurter.core.Core
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.loading.BaseLoadResultMapper
import com.kliachenko.presentation.loading.LoadUiObservable
import com.kliachenko.presentation.loading.LoadViewModel

class LoadModule(
    private val core: Core,
    private val provideInstance: ProvideInstance.ProvideLoadRepository,
    private val clear: Clear,
) : Module<LoadViewModel> {

    override fun viewModel(): LoadViewModel {
        val observable = LoadUiObservable.Base()
        return LoadViewModel(
            observable = observable,
            repository = provideInstance.provideLoadRepository(
                cloudDataSource = LoadCurrencyCloudDataSource.Base(
                    core.provideRetrofit().retrofit().create(CurrencyService::class.java)
                ),
                cacheDataSource = CurrencyCacheDataSource.Base(
                    core.provideCurrencyDataBase().dataBase().currencyDao()
                ),
                provideResources = core.provideResources()
            ),
            runAsync = core.provideRunAsync(),
            mapper = BaseLoadResultMapper(
                observable = observable,
                core.provideNavigation(),
                clear
            )
        )
    }
}