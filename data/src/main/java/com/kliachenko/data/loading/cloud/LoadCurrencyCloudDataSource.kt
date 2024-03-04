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
}