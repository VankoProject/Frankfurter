package com.kliachenko.data.loading.cloud

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

    class Fake: LoadCurrencyCloudDataSource {
        override suspend fun loadCurrencies(): HashMap<String, String> {
           return hashMapOf(
                "USD" to "United States Dollar",
                "EUR" to "Euro",
                "JPY" to "Japanese Yen",
            )
        }

    }
}