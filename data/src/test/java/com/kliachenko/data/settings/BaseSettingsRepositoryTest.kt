package com.kliachenko.data.settings

import com.kliachenko.data.dashboard.FakeFavoritePairCacheDataSource
import com.kliachenko.data.dashboard.cache.CurrencyPair
import com.kliachenko.data.loading.cache.CurrencyCache
import com.kliachenko.data.loading.cache.CurrencyCacheDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BaseSettingsRepositoryTest {

    private lateinit var currencyCacheDataSource: FakeCurrencyCacheDataSource
    private lateinit var favoritePairCacheDataSource: FakeFavoritePairCacheDataSource
    private lateinit var repository: BaseSettingsRepository

    @Before
    fun setup() {
        currencyCacheDataSource = FakeCurrencyCacheDataSource()
        favoritePairCacheDataSource = FakeFavoritePairCacheDataSource()
        repository = BaseSettingsRepository(
            currencyCacheDataSource = currencyCacheDataSource,
            favoritePairCacheDataSource = favoritePairCacheDataSource
        )
    }

    @Test
    fun test() = runBlocking {
        var expected = listOf("A", "B", "C", "D")
        var actual = repository.allCurrencies()
        Assert.assertEquals(expected, actual)

        actual = repository.availableCurrenciesDestinations("A")
        expected = listOf("B", "C", "D")
        Assert.assertEquals(expected, actual)

        repository.save("A", "B")
        favoritePairCacheDataSource.checkSaved(
            listOf(
                CurrencyPair("A", "B"),
            )
        )
        actual = repository.availableCurrenciesDestinations("A")
        expected = listOf("C", "D")
        Assert.assertEquals(expected, actual)

        repository.save("A", "C")
        favoritePairCacheDataSource.checkSaved(
            listOf(
                CurrencyPair("A", "B"),
                CurrencyPair("A", "C"),

            )
        )
        actual = repository.availableCurrenciesDestinations("A")
        expected = listOf("D")
        Assert.assertEquals(expected, actual)

        repository.save("A", "D")
        favoritePairCacheDataSource.checkSaved(
            listOf(
                CurrencyPair("A", "B"),
                CurrencyPair("A", "C"),
                CurrencyPair("A", "D"),
                )
        )
        actual = repository.availableCurrenciesDestinations("A")
        expected = emptyList()
        Assert.assertEquals(expected, actual)

        actual = repository.availableCurrenciesDestinations("B")
        expected = listOf("A", "C", "D")
        Assert.assertEquals(expected, actual)

        actual = repository.availableCurrenciesDestinations("C")
        expected = listOf("A", "B", "D")
        Assert.assertEquals(expected, actual)
    }
}

private class FakeCurrencyCacheDataSource : CurrencyCacheDataSource.Read {

    override suspend fun read(): List<CurrencyCache> =
        listOf(
            CurrencyCache("A", "1"),
            CurrencyCache("B", "2"),
            CurrencyCache("C", "3"),
            CurrencyCache("D", "3")
        )

}