package com.kliachenko.frankfurter.di.source

import com.kliachenko.data.dashboard.cloud.currencyLoad.CurrencyService
import com.kliachenko.data.dashboard.cloud.currencyLoad.LoadCurrencyCloudDataSource
import com.kliachenko.data.dashboard.cloud.currencyRate.CurrencyRateCloudDataSource
import com.kliachenko.data.dashboard.cloud.currencyRate.CurrencyRateService
import com.kliachenko.frankfurter.core.ProvideInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CloudModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideCurrencyService(retrofit: Retrofit): CurrencyService =
        retrofit.create(CurrencyService::class.java)

    @Provides
    @Singleton
    fun provideCurrencyRateService(retrofit: Retrofit): CurrencyRateService =
        retrofit.create(CurrencyRateService::class.java)

    @Provides
    @Singleton
    fun provideCurrencyRateCloudDataSource(
        provideInstance: ProvideInstance,
        retrofit: Retrofit,
    ): CurrencyRateCloudDataSource {
        return provideInstance.provideLoadRateCloudDataSource(retrofit)
    }

    @Provides
    @Singleton
    fun provideLoadCurrencyCloudDataSource(
        provideInstance: ProvideInstance,
        retrofit: Retrofit,
    ): LoadCurrencyCloudDataSource {
        return provideInstance.provideLoadCloudDataSource(retrofit)
    }

    companion object {
        private const val BASE_URL = "https://www.frankfurter.app/"
    }

}