package com.kliachenko.frankfurter.core.module

import com.kliachenko.data.core.ProvideRetrofit
import com.kliachenko.data.dashboard.cloud.CurrencyRateCloudDataSource
import com.kliachenko.data.dashboard.cloud.CurrencyRateService
import com.kliachenko.data.loading.cloud.CurrencyService
import com.kliachenko.data.loading.cloud.LoadCurrencyCloudDataSource

interface ProvideInstance {

    fun provideLoadCloudDataSource(provideRetrofit: ProvideRetrofit): LoadCurrencyCloudDataSource

    fun provideLoadRateCloudDataSource(provideRetrofit: ProvideRetrofit): CurrencyRateCloudDataSource

    fun provideFreeCountPair(): Int


    class Base : ProvideInstance {
        override fun provideLoadCloudDataSource(provideRetrofit: ProvideRetrofit): LoadCurrencyCloudDataSource {
            return LoadCurrencyCloudDataSource.Base(
                provideRetrofit.retrofit().create(CurrencyService::class.java)
            )
        }

        override fun provideLoadRateCloudDataSource(provideRetrofit: ProvideRetrofit): CurrencyRateCloudDataSource {
            return CurrencyRateCloudDataSource.Base(
                provideRetrofit.retrofit().create(CurrencyRateService::class.java)
            )
        }

        override fun provideFreeCountPair() = 5

    }

    class Mock : ProvideInstance {

        override fun provideLoadCloudDataSource(provideRetrofit: ProvideRetrofit): LoadCurrencyCloudDataSource {
            return LoadCurrencyCloudDataSource.Fake()
        }

        override fun provideLoadRateCloudDataSource(provideRetrofit: ProvideRetrofit): CurrencyRateCloudDataSource {
            return CurrencyRateCloudDataSource.Fake()
        }

        override fun provideFreeCountPair() = 2

    }
}

