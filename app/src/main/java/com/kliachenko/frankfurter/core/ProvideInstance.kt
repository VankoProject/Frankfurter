package com.kliachenko.frankfurter.core

import com.kliachenko.data.cache.CacheDataSource
import com.kliachenko.data.main.BaseMainRepository
import com.kliachenko.domain.MainRepository

interface ProvideInstance {

    fun provideRepository(
        cacheDataSource: CacheDataSource.Mutable,
    ): MainRepository

    class Base : ProvideInstance {
        override fun provideRepository(cacheDataSource: CacheDataSource.Mutable): MainRepository {
            return BaseMainRepository(cacheDataSource)
        }
    }
}