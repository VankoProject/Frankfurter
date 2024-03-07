package com.kliachenko.domain.settings

interface SettingsRepository {

    suspend fun allCurrencies(): List<String>

    suspend fun availableCurrenciesDestinations(from: String): List<String>

    suspend fun save(from: String, to: String)

}