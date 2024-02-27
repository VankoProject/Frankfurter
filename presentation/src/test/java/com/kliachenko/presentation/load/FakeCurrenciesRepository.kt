package com.kliachenko.presentation.load

import com.kliachenko.domain.load.LoadCurrenciesRepository
import com.kliachenko.domain.load.LoadCurrenciesResult

class FakeCurrenciesRepository : LoadCurrenciesRepository {

    private lateinit var result: LoadCurrenciesResult

    fun expectSuccess() {
        result = LoadCurrenciesResult.Success
    }

    fun expectError() {
        result = LoadCurrenciesResult.Error(message = "No internet connection")
    }

    override suspend fun loadCurrencies(): LoadCurrenciesResult {
        return result
    }


}