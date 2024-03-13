package com.kliachenko.data.loading.cloud

import java.net.UnknownHostException
import javax.inject.Inject

interface LoadCurrencyCloudDataSource {

    suspend fun loadCurrencies(): HashMap<String, String>

    class Base @Inject constructor(
        private val service: CurrencyService,
    ) : LoadCurrencyCloudDataSource {

        override suspend fun loadCurrencies(): HashMap<String, String> {
            val response = service.currencies().execute()
            return response.body()!!
        }
    }

    class Fake @Inject constructor() : LoadCurrencyCloudDataSource {

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