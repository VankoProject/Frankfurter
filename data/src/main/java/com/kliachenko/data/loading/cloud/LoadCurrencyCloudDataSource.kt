package com.kliachenko.data.loading.cloud

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface LoadCurrencyCloudDataSource {

    suspend fun loadCurrencies(): HashMap<String, String>

    class Base(
        private val service: CurrencyService,
    ) : LoadCurrencyCloudDataSource {

        constructor(): this(
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
                .create(CurrencyService::class.java)
        )

        override suspend fun loadCurrencies(): HashMap<String, String> {
            val response = service.currencies().execute()
            return response.body()!!
        }
    }
}