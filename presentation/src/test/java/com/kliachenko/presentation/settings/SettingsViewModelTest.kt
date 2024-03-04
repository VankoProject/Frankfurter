package com.kliachenko.presentation.settings

import com.kliachenko.data.dashboard.cache.CurrencyPair
import com.kliachenko.domain.settings.SettingsRepository
import com.kliachenko.presentation.core.UiObservable
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.fake.FakeClear
import com.kliachenko.presentation.fake.FakeNavigation
import com.kliachenko.presentation.fake.FakeRunAsync
import com.kliachenko.presentation.settings.adapter.CurrencyChoiceUi
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SettingsViewModelTest {

    private lateinit var observable: FakeSettingsUiObservable
    private lateinit var navigation: FakeNavigation
    private lateinit var clear: FakeClear
    private lateinit var repository: FakeSettingsRepository
    private lateinit var runAsync: FakeRunAsync
    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setup() {
        observable = FakeSettingsUiObservable()
        navigation = FakeNavigation()
        clear = FakeClear()
        repository = FakeSettingsRepository()
        runAsync = FakeRunAsync()
        viewModel = SettingsViewModel(
            repository = repository,
            navigation = navigation,
            clear = clear,
            observable = observable,
            runAsync = runAsync
        )
    }

    @Test
    fun happyPass() {
        viewModel.init()
        runAsync.returnLoadResult()
        observable.checkUiSate(
            expected = SettingsUiState.FirstChoice(
                listOf(
                    CurrencyChoiceUi.Base(currency = "USD", isSelected = false),
                    CurrencyChoiceUi.Base(currency = "EUR", isSelected = false),
                    CurrencyChoiceUi.Base(currency = "JPY", isSelected = false),
                )
            )
        )

        viewModel.chooseFirstCurrency(currency = "USD")
        runAsync.returnLoadResult()
        observable.checkUiSate(
            expected = SettingsUiState.SecondChoice(
                fromCurrency = listOf(
                    CurrencyChoiceUi.Base(currency = "USD", isSelected = true),
                    CurrencyChoiceUi.Base(currency = "EUR", isSelected = false),
                    CurrencyChoiceUi.Base(currency = "JPY", isSelected = false),
                ),
                toCurrency = listOf(
                    CurrencyChoiceUi.Base(currency = "EUR", isSelected = false),
                    CurrencyChoiceUi.Base(currency = "JPY", isSelected = false),
                )
            )
        )

        viewModel.chooseSecondCurrency(from = "USD", to = "EUR")
        runAsync.returnLoadResult()
        observable.checkUiSate(
            SettingsUiState.Save(
                toCurrency = listOf(
                    CurrencyChoiceUi.Base(currency = "EUR", isSelected = true),
                    CurrencyChoiceUi.Base(currency = "JPY", isSelected = false),
                )
            )
        )

        viewModel.save(fromCurrency = "USD", toCurrency = "EUR")
        runAsync.returnLoadResult()
        repository.checkSavedSelectedPair(
            listOf(
                CurrencyPair(
                    fromCurrency = "USD",
                    toCurrency = "EUR"
                )
            )
        )
        navigation.checkNavigateToDashBoardScreen()
        clear.checkCalled(SettingsViewModel::class.java)

        viewModel.chooseFirstCurrency(currency = "USD")
        runAsync.returnLoadResult()
        viewModel.chooseSecondCurrency(from = "USD", to = "JPY")
        runAsync.returnLoadResult()
        observable.checkUiSate(
            SettingsUiState.Save(
                toCurrency = listOf(
                    CurrencyChoiceUi.Base(currency = "JPY", isSelected = true),
                )
            )
        )

        viewModel.save(fromCurrency = "USD", toCurrency = "JPY")
        runAsync.returnLoadResult()
        repository.checkSavedSelectedPair(
            listOf(
                CurrencyPair(
                    fromCurrency = "USD",
                    toCurrency = "EUR"
                ),
                CurrencyPair(
                    fromCurrency = "USD",
                    toCurrency = "JPY"
                )
            )
        )

        viewModel.chooseFirstCurrency(currency = "USD")
        runAsync.returnLoadResult()
        observable.checkUiSate(
            SettingsUiState.SecondChoice(
                fromCurrency = listOf(CurrencyChoiceUi.Base(currency = "USD", isSelected = true),
                CurrencyChoiceUi.Base(currency = "EUR", isSelected = false),
                CurrencyChoiceUi.Base(currency = "JPY", isSelected = false),
            ),
                toCurrency = listOf(CurrencyChoiceUi.Empty)
        ))

        navigation.checkNavigateToDashBoardScreen()
        clear.checkCalled(SettingsViewModel::class.java)

    }

    @Test
    fun bachDashBoard() {
        viewModel.backDashBoard()
        navigation.checkNavigateToDashBoardScreen()
        clear.checkCalled(SettingsViewModel::class.java)
    }

    @Test
    fun lifeCycle() {
        val observer: UpdateUi<SettingsUiState> = object : UpdateUi<SettingsUiState> {
            override fun updateUi(uiState: SettingsUiState) = Unit
        }
        viewModel.startGettingUpdates(observer = observer)
        observable.checkObserver(observer)

        viewModel.stopGettingUpdates()
        observable.checkEmpty(observer)
    }
}

private class FakeSettingsRepository : SettingsRepository {

    private var actualCurrencies = mutableListOf("USD", "EUR", "JPY")
    private var actualSavedCurrencyPair = mutableListOf<CurrencyPair>()

    override suspend fun allCurrencies(): List<String> {
        return actualCurrencies
    }

    override suspend fun availableCurrenciesDestinations(from: String): List<String> {
        val toCurrencies = mutableListOf<String>()
        toCurrencies.clear()
        toCurrencies.addAll(actualCurrencies)
        toCurrencies.remove(from)
        toCurrencies.removeAll(
            actualSavedCurrencyPair.filter { it.fromCurrency == from }.map {
                it.toCurrency
            }
        )
        return toCurrencies
    }

    override suspend fun save(from: String, to: String) {
        actualSavedCurrencyPair.add(CurrencyPair(from, to))
    }

    fun checkSavedSelectedPair(expected: List<CurrencyPair>) {
        assertEquals(expected, actualSavedCurrencyPair)
    }

}

private class FakeSettingsUiObservable : UiObservable<SettingsUiState> {

    private var actualUiState: SettingsUiState = SettingsUiState.Initial
    private var actualObserver: UpdateUi<SettingsUiState> = UpdateUi.Empty()

    override fun updateObserver(observer: UpdateUi<SettingsUiState>) {
        actualObserver = observer
    }

    override fun updateUi(uiState: SettingsUiState) {
        actualUiState = uiState
    }

    fun checkUiSate(expected: SettingsUiState) {
        assertEquals(expected, actualUiState)
    }

    fun checkObserver(expected: UpdateUi<SettingsUiState>) {
        assertEquals(expected, actualObserver)
    }

    fun checkEmpty(expected: UpdateUi<SettingsUiState>) {
        Assert.assertNotEquals(expected, actualObserver)
    }

}