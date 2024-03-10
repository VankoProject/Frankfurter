package com.kliachenko.domain.settings

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class SettingsInteractorTest {

    private lateinit var repository: FakeSettingsRepository
    private lateinit var premiumUserStorage: FakePremiumUserStorage
    private lateinit var interactor: SettingsInteractor

    @Before
    fun setup() {
        repository = FakeSettingsRepository()
        premiumUserStorage = FakePremiumUserStorage()
        interactor = SettingsInteractor.Base(
            settingsRepository = repository,
            premiumUserStorage = premiumUserStorage,
            freeCountPair = 2
        )
    }

    @Test
    fun test() = runBlocking {
        var expectedCurrencies: List<String> = listOf("USD", "EUR", "JPY")
        var actualCurrencies: List<String> = interactor.allCurrencies()
        assertEquals(expectedCurrencies, actualCurrencies)

        expectedCurrencies = listOf("EUR", "JPY")
        actualCurrencies = interactor.availableCurrenciesDestinations("USD")
        assertEquals(expectedCurrencies, actualCurrencies)

        var expectedResult: SaveResult = SaveResult.Success
        var actualResult = interactor.save("USD", "EUR")
        repository.checkSavedPair(listOf(Pair("USD", "EUR")))
        assertEquals(expectedResult, actualResult)

        expectedCurrencies = listOf("JPY")
        actualCurrencies = interactor.availableCurrenciesDestinations("USD")
        assertEquals(expectedCurrencies, actualCurrencies)

        expectedResult = SaveResult.Success
        actualResult = interactor.save("USD", "JPY")
        repository.checkSavedPair(listOf(Pair("USD", "EUR"), Pair("USD", "JPY")))
        assertEquals(expectedResult, actualResult)

        expectedCurrencies = listOf("USD", "JPY")
        actualCurrencies = interactor.availableCurrenciesDestinations("EUR")
        assertEquals(expectedCurrencies, actualCurrencies)

        expectedResult = SaveResult.RequirePremium
        actualResult = interactor.save("EUR", "JPY")
        repository.checkSavedPair(listOf(Pair("USD", "EUR"), Pair("USD", "JPY")))
        assertEquals(expectedResult, actualResult)

        premiumUserStorage.savePremiumUser()
        expectedResult = SaveResult.Success
        actualResult = interactor.save("EUR", "JPY")
        repository.checkSavedPair(
            listOf(
                Pair("USD", "EUR"),
                Pair("USD", "JPY"),
                Pair("EUR", "JPY")
            )
        )
        assertEquals(expectedResult, actualResult)

    }
}

private class FakeSettingsRepository : SettingsRepository {

    private var actualCurrencies: List<String> = emptyList()
    private var savedFavoritePairs = mutableListOf<Pair<String, String>>()

    override suspend fun allCurrencies(): List<String> {
        actualCurrencies = listOf("USD", "EUR", "JPY")
        return actualCurrencies
    }

    override suspend fun availableCurrenciesDestinations(from: String): List<String> {
        val result = mutableListOf<String>().apply {
            addAll(actualCurrencies)
            remove(from)
            removeAll(savedFavoritePairs.filter { pair -> pair.first == from }
                .map { pair -> pair.second })
        }
        return result
    }

    override suspend fun save(from: String, to: String) {
        savedFavoritePairs.add(Pair(from, to))
    }

    override suspend fun savedPairsCount() = savedFavoritePairs.size

    fun checkSavedPair(expected: List<Pair<String, String>>) {
        assertEquals(expected, savedFavoritePairs)
    }

}

private class FakePremiumUserStorage : PremiumUserStorage.Mutable {

    private var actual: Boolean = false

    override fun savePremiumUser() {
        actual = true
    }

    override fun isPremium() = actual

}