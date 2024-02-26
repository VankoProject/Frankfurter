package com.kliachenko.data.cloud

interface CloudDataSource {

    suspend fun load(): HashMap<String, String>

    class Base(
        private val service: CurrencyService,
    ) : CloudDataSource {

        override suspend fun load(): HashMap<String, String> {
            val response = service.currencies().execute()
            val body = response.body()!!
            return body
        }
    }
}