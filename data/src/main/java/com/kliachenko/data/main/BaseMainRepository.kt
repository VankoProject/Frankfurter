package com.kliachenko.data.main

import com.kliachenko.data.cache.CacheDataSource
import com.kliachenko.data.cloud.CloudDataSource
import com.kliachenko.data.cloud.CurrencyService
import com.kliachenko.domain.LoadResult
import com.kliachenko.domain.MainRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException

class BaseMainRepository(
    private val cloudDataSource: CloudDataSource,
    private val cacheDataSource: CacheDataSource.Mutable
) : MainRepository {

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
            cacheDataSource = cacheDataSource
        ),
        cacheDataSource = cacheDataSource
    )

    override suspend fun loadCurrencies(): LoadResult = try {
        cloudDataSource.load()
        LoadResult.Success
    } catch (e: Exception) {
        if (e is UnknownHostException)
            LoadResult.Error("No internet connection")
        else
            LoadResult.Error("Service unavailable")
    }

    override suspend fun hasCurrencies(): Boolean {
        return cacheDataSource.currencies().isNotEmpty()
    }
}