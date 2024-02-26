package com.kliachenko.frankfurter.core

import com.kliachenko.data.loading.BaseLoadCurrencyRepository
import com.kliachenko.data.loading.cache.CurrencyCacheDataSource
import com.kliachenko.domain.LoadCurrenciesRepository

interface ProvideInstance {

    fun provideRepository(
        currencyCacheDataSource: CurrencyCacheDataSource.Mutable,
    ): LoadCurrenciesRepository

    class Base : ProvideInstance {
        override fun provideRepository(currencyCacheDataSource: CurrencyCacheDataSource.Mutable): LoadCurrenciesRepository {
            return BaseLoadCurrencyRepository(currencyCacheDataSource)
        }
    }
}