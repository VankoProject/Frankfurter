package com.kliachenko.frankfurter.core

import com.kliachenko.data.cache.CacheDataSource
import com.kliachenko.data.main.BaseLoadCurrenciesRepository
import com.kliachenko.domain.LoadCurrenciesRepository

interface ProvideInstance {

    fun provideRepository(
        cacheDataSource: CacheDataSource.Mutable,
    ): LoadCurrenciesRepository

    class Base : ProvideInstance {
        override fun provideRepository(cacheDataSource: CacheDataSource.Mutable): LoadCurrenciesRepository {
            return BaseLoadCurrenciesRepository(cacheDataSource)
        }
    }
}