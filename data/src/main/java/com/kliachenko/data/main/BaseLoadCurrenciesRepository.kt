package com.kliachenko.data.main

import com.kliachenko.data.cache.CacheDataSource
import com.kliachenko.data.cache.CurrencyCache
import com.kliachenko.data.cloud.CloudDataSource
import com.kliachenko.data.cloud.CurrencyService
import com.kliachenko.domain.LoadCurrenciesRepository
import com.kliachenko.domain.LoadCurrenciesResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException

class BaseLoadCurrenciesRepository(
    private val cloudDataSource: CloudDataSource,
    private val cacheDataSource: CacheDataSource.Mutable,
) : LoadCurrenciesRepository {

    constructor(
        cacheDataSource: CacheDataSource.Mutable,
    ) : this(
        CloudDataSource.Base(
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
                .create(CurrencyService::class.java),
        ),
        cacheDataSource = cacheDataSource
    )

    override suspend fun loadCurrencies(): LoadCurrenciesResult = try {
        if (cacheDataSource.currencies().isEmpty()) {
            val result = cloudDataSource.load().map {
                CurrencyCache(it.key, it.value)
            }
            cacheDataSource.saveCurrency(result)
        }
        LoadCurrenciesResult.Success
    } catch (e: Exception) {
        if (e is UnknownHostException)
            LoadCurrenciesResult.Error("No internet connection")
        else
            LoadCurrenciesResult.Error("Service unavailable")
    }
}