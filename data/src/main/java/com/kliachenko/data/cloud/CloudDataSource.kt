package com.kliachenko.data.cloud

import com.kliachenko.data.cache.CacheDataSource
import com.kliachenko.data.cache.CurrencyCache

interface CloudDataSource {

    suspend fun load()

    class Base(
        private val service: CurrencyService,
        private val cacheDataSource: CacheDataSource.Save
    ) : CloudDataSource {

        override suspend fun load() {
            val response = service.currencies().execute()
            val body = response.body()!!
            val listResult = mutableListOf<CurrencyCache>()
            body.forEach { (code, fullName) ->
                val temp = CurrencyCache(code, fullName)
                listResult.add(temp)
            }
            cacheDataSource.saveCurrency(listResult)
        }
    }
}