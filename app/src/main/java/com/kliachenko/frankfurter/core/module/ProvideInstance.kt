package com.kliachenko.frankfurter.core.module

import com.kliachenko.data.ProvideResources
import com.kliachenko.data.loading.BaseLoadCurrencyRepository
import com.kliachenko.data.loading.cache.CurrencyCacheDataSource
import com.kliachenko.data.loading.cloud.LoadCurrencyCloudDataSource
import com.kliachenko.domain.load.LoadCurrenciesRepository

interface ProvideInstance {

    fun provideLoadRepository(
        cacheDataSource: CurrencyCacheDataSource.Mutable,
        cloudDataSource: LoadCurrencyCloudDataSource,
        provideResources: ProvideResources,
    ): LoadCurrenciesRepository

    class Base : ProvideInstance {
        override fun provideLoadRepository(
            cacheDataSource: CurrencyCacheDataSource.Mutable,
            cloudDataSource: LoadCurrencyCloudDataSource,
            provideResources: ProvideResources,
        ): LoadCurrenciesRepository {
            return BaseLoadCurrencyRepository(
                cloudDataSource, cacheDataSource, provideResources
            )
        }
    }
}