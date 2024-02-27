package com.kliachenko.frankfurter.core

import com.kliachenko.data.ProvideResources
import com.kliachenko.data.loading.BaseLoadCurrencyRepository
import com.kliachenko.data.loading.cache.CurrencyCacheDataSource
import com.kliachenko.data.loading.cloud.LoadCurrencyCloudDataSource
import com.kliachenko.domain.LoadCurrenciesRepository

interface ProvideInstance {

    fun provideRepository(
        loadCurrencyCloudDataSource: LoadCurrencyCloudDataSource,
        currencyCacheDataSource: CurrencyCacheDataSource.Mutable,
        provideResources: ProvideResources,
    ): LoadCurrenciesRepository

    class Base : ProvideInstance {
        override fun provideRepository(
            loadCurrencyCloudDataSource: LoadCurrencyCloudDataSource,
            currencyCacheDataSource: CurrencyCacheDataSource.Mutable,
            provideResources: ProvideResources,
        ): LoadCurrenciesRepository {
            return BaseLoadCurrencyRepository(
                loadCurrencyCloudDataSource,
                currencyCacheDataSource,
                provideResources
            )
        }
    }
}