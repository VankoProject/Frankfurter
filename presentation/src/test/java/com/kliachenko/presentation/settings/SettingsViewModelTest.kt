package com.kliachenko.presentation.settings

import com.kliachenko.domain.settings.SaveResult
import com.kliachenko.domain.settings.SettingsInteractor
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
    private lateinit var interactor: FakeSettingsInteractor
    private lateinit var navigation: FakeNavigation
    private lateinit var clear: FakeClear
    private lateinit var runAsync: FakeRunAsync
    private lateinit var viewModel: SettingsViewModel
    private lateinit var bundleWrapper: FakeBundleWrapper

    @Before
    fun setup() {
        observable = FakeSettingsUiObservable()
        interactor = FakeSettingsInteractor()
        navigation = FakeNavigation()
        bundleWrapper = FakeBundleWrapper()
        clear = FakeClear()
        runAsync = FakeRunAsync()
        viewModel = SettingsViewModel(
            interactor = interactor,
            navigation = navigation,
            clear = clear,
            observable = observable,
            runAsync = runAsync
        )
    }

    @Test
    fun userIsNotPremium() {
        viewModel.init(bundleWrapper)
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
                ),
                fromCurrency = listOf(
                    CurrencyChoiceUi.Base(currency = "USD", isSelected = true),
                    CurrencyChoiceUi.Base(currency = "EUR", isSelected = false),
                    CurrencyChoiceUi.Base(currency = "JPY", isSelected = false),
                )
            )
        )

        viewModel.save(fromCurrency = "USD", toCurrency = "EUR")
        runAsync.returnLoadResult()
        interactor.checkSavedSelectedPair(
            listOf(
                Pair(
                    "USD",
                    "EUR"
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
                ),
                fromCurrency = listOf(
                    CurrencyChoiceUi.Base(currency = "USD", isSelected = true),
                    CurrencyChoiceUi.Base(currency = "EUR", isSelected = false),
                    CurrencyChoiceUi.Base(currency = "JPY", isSelected = false),
                )
            )
        )

        viewModel.save(fromCurrency = "USD", toCurrency = "JPY")
        runAsync.returnLoadResult()
        interactor.checkSavedSelectedPair(listOf(Pair("USD", "EUR"), Pair("USD", "JPY")))
        clear.checkCalled(SettingsViewModel::class.java)
        navigation.checkNavigateToDashBoardScreen()

        viewModel.chooseFirstCurrency(currency = "USD")
        runAsync.returnLoadResult()
        observable.checkUiSate(
            SettingsUiState.SecondChoice(
                fromCurrency = listOf(
                    CurrencyChoiceUi.Base(currency = "USD", isSelected = true),
                    CurrencyChoiceUi.Base(currency = "EUR", isSelected = false),
                    CurrencyChoiceUi.Base(currency = "JPY", isSelected = false),
                ),
                toCurrency = listOf(CurrencyChoiceUi.Empty)
            )
        )

        viewModel.chooseFirstCurrency(currency = "EUR")
        runAsync.returnLoadResult()
        viewModel.chooseSecondCurrency(from = "EUR", to = "JPY")
        runAsync.returnLoadResult()
        viewModel.save(fromCurrency = "EUR", toCurrency = "JPY")
        runAsync.returnLoadResult()
        interactor.checkSavedSelectedPair(listOf(Pair("USD", "EUR"), Pair("USD", "JPY")))
        navigation.checkNavigateToSubscriptionScreen()
        clear.checkCalled(SettingsViewModel::class.java)

        interactor.userHasPremium()
        viewModel.save(fromCurrency = "EUR", toCurrency = "JPY")
        runAsync.returnLoadResult()
        interactor.checkSavedSelectedPair(listOf(Pair("USD", "EUR"), Pair("USD", "JPY"), Pair("EUR", "JPY")))
        navigation.checkNavigateToDashBoardScreen()
        clear.checkCalled(SettingsViewModel::class.java)
    }

    @Test
    fun userIsPremium() {
        interactor.userHasPremium()

        viewModel.init(bundleWrapper = FakeBundleWrapper())
        runAsync.returnLoadResult()

        viewModel.chooseFirstCurrency(currency = "USD")
        runAsync.returnLoadResult()
        viewModel.chooseSecondCurrency(from = "USD", to = "EUR")
        runAsync.returnLoadResult()
        viewModel.save(fromCurrency = "USD", toCurrency = "EUR")
        runAsync.returnLoadResult()
        navigation.checkNavigateToDashBoardScreen()

        viewModel.chooseFirstCurrency(currency = "USD")
        runAsync.returnLoadResult()
        viewModel.chooseSecondCurrency(from = "USD", to = "JPY")
        runAsync.returnLoadResult()
        viewModel.save(fromCurrency = "USD", toCurrency = "JPY")
        runAsync.returnLoadResult()
        navigation.checkNavigateToDashBoardScreen()

        viewModel.chooseFirstCurrency(currency = "EUR")
        runAsync.returnLoadResult()
        viewModel.chooseSecondCurrency(from = "EUR", to = "JPY")
        runAsync.returnLoadResult()
        viewModel.save(fromCurrency = "EUR", toCurrency = "JPY")
        runAsync.returnLoadResult()
        interactor.checkSavedSelectedPair(listOf(Pair("USD", "EUR"), Pair("USD", "JPY"), Pair("EUR", "JPY")))
        navigation.checkNavigateToDashBoardScreen()
    }

    @Test
    fun backDashBoard() {
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

private class FakeSettingsInteractor : SettingsInteractor {

    private var actualCurrencies: List<String> = emptyList()
    private var savedFavoritePairs: MutableList<Pair<String, String>> = mutableListOf()
    private var isPremiun: Boolean = false
    private var maxFreePairs: Int = 2

    override suspend fun allCurrencies(): List<String> {
        actualCurrencies = listOf("USD", "EUR", "JPY")
        return actualCurrencies
    }

    override suspend fun availableCurrenciesDestinations(from: String): List<String> {
        val toCurrencies = mutableListOf<String>()
        toCurrencies.addAll(actualCurrencies)
        toCurrencies.remove(from)
        toCurrencies.removeAll(
            savedFavoritePairs.filter { it.first == from }.map {
                it.second
            }
        )
        return toCurrencies
    }

    override suspend fun save(from: String, to: String): SaveResult {
        return if (savedFavoritePairs.size < maxFreePairs || isPremiun) {
            savedFavoritePairs.add(Pair(from, to))
            SaveResult.Success
        } else {
            SaveResult.RequirePremium
        }
    }

    fun userHasPremium() {
        isPremiun = true
    }

    fun checkSavedSelectedPair(expected: List<Pair<String, String>>) {
        assertEquals(expected, savedFavoritePairs)
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

private class FakeBundleWrapper : BundleWrapper.Mutable {

    private var actualFromCurrency: String = "USD"
    private var actualToCurrency: String = "EUR"
    private var isEmpty: Boolean = true


    override fun save(fromCurrency: String, toCurrency: String) {
        actualFromCurrency = fromCurrency
        actualToCurrency = toCurrency
    }

    override fun restore(): Pair<String, String> =
        Pair(actualFromCurrency, actualToCurrency)

    override fun isEmpty() = isEmpty

}