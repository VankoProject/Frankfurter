package com.kliachenko.data.loading.cloud

import java.net.UnknownHostException

interface LoadCurrencyCloudDataSource {

    suspend fun loadCurrencies(): HashMap<String, String>

    class Base(
        private val service: CurrencyService,
    ) : LoadCurrencyCloudDataSource {

        override suspend fun loadCurrencies(): HashMap<String, String> {
            val response = service.currencies().execute()
            return response.body()!!
        }
    }

    class Fake : LoadCurrencyCloudDataSource {

        private var firstTime: Boolean = true

        override suspend fun loadCurrencies(): HashMap<String, String> {
            if (firstTime) {
                firstTime = false
                throw UnknownHostException()
            }
            return hashMapOf(
                "USD" to "United States Dollar",
                "EUR" to "Euro",
                "JPY" to "Japanese Yen",
            )
        }

    }
}