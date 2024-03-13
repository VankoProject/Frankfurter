package com.kliachenko.data.settings

import com.kliachenko.data.dashboard.cache.CurrencyPair
import com.kliachenko.data.dashboard.cache.FavoritePairCacheDataSource
import com.kliachenko.data.loading.cache.CurrencyCacheDataSource
import com.kliachenko.domain.settings.SettingsRepository
import javax.inject.Inject

class BaseSettingsRepository @Inject constructor(
    private val favoritePairCacheDataSource: FavoritePairCacheDataSource.Mutable,
    private val currencyCacheDataSource: CurrencyCacheDataSource.Read,
) : SettingsRepository {

    override suspend fun allCurrencies(): List<String> {
        return currencyCacheDataSource.read().map {
            it.code
        }.sortedBy { it }
    }

    override suspend fun availableCurrenciesDestinations(from: String): List<String> {
        val currencies = allCurrencies()
        val favoriteCurrencies = favoritePairCacheDataSource.favoriteCurrencyPairs().filter {
            it.fromCurrency == from
        }.map { it.toCurrency }
        return currencies.filterNot { it == from || it in favoriteCurrencies }.sortedBy { it }
    }

    override suspend fun save(from: String, to: String) {
        favoritePairCacheDataSource.saveFavoritePair(CurrencyPair(from, to))
    }

    override suspend fun savedPairsCount(): Int {
        return favoritePairCacheDataSource.favoriteCurrencyPairs().size
    }
}