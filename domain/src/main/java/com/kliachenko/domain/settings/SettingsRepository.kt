package com.kliachenko.domain.settings

import com.kliachenko.domain.BaseRepository

interface SettingsRepository: BaseRepository {

    suspend fun allCurrencies(): List<String>

    suspend fun availableCurrenciesDestinations(from: String): List<String>

    suspend fun save(from: String, to: String)

}