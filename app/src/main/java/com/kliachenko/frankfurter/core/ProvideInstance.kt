package com.kliachenko.frankfurter.core

import com.kliachenko.data.dashboard.cloud.CurrencyRateCloudDataSource
import com.kliachenko.data.dashboard.cloud.CurrencyRateService
import com.kliachenko.data.loading.cloud.CurrencyService
import com.kliachenko.data.loading.cloud.LoadCurrencyCloudDataSource
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

interface ProvideInstance {

    fun provideLoadCloudDataSource(retrofit: Retrofit): LoadCurrencyCloudDataSource

    fun provideLoadRateCloudDataSource(retrofit: Retrofit): CurrencyRateCloudDataSource

    fun provideFreeCountPair(): Int


    @Singleton
    class Base @Inject constructor(): ProvideInstance {
        override fun provideLoadCloudDataSource(retrofit: Retrofit): LoadCurrencyCloudDataSource {
            return LoadCurrencyCloudDataSource.Base(
                retrofit.create(CurrencyService::class.java)
            )
        }

        override fun provideLoadRateCloudDataSource(retrofit: Retrofit): CurrencyRateCloudDataSource {
            return CurrencyRateCloudDataSource.Base(
                retrofit.create(CurrencyRateService::class.java)
            )
        }

        override fun provideFreeCountPair() = 5

    }

    @Singleton
    class Mock @Inject constructor(): ProvideInstance {

        override fun provideLoadCloudDataSource(retrofit: Retrofit): LoadCurrencyCloudDataSource {
            return LoadCurrencyCloudDataSource.Fake()
        }

        override fun provideLoadRateCloudDataSource(retrofit: Retrofit): CurrencyRateCloudDataSource {
            return CurrencyRateCloudDataSource.Fake()
        }

        override fun provideFreeCountPair() = 2

    }
}

