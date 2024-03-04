package com.kliachenko.data.dashboard.cloud

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface CurrencyRateCloudDataSource {

    suspend fun rate(fromCurrency: String, toCurrency: String): Double

    class Base(private val service: CurrencyRateService) : CurrencyRateCloudDataSource {

        constructor() : this(
            service = Retrofit.Builder()
                .baseUrl("https://www.frankfurter.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }).build()
                )
                .build()
                .create(CurrencyRateService::class.java)
        )

        override suspend fun rate(fromCurrency: String, toCurrency: String): Double {
            val currencyPair: CurrencyPairCloud =
                service.currencyValue(fromCurrency, toCurrency).execute().body()!!
            return currencyPair.rate(toCurrency)
        }

    }
}