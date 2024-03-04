package com.kliachenko.presentation.settings

//class SettingsViewModelTest {
//
//    private lateinit var observable: FakeSettingsUiObservable
//    private lateinit var navigation: FakeNavigation
//    private lateinit var clear: FakeClear
//    private lateinit var repository: FakeSettingsRepository
//    private lateinit var runAsync: FakeRunAsync
//    private lateinit var viewModel: SettingsViewModel
//
//    @Before
//    fun setup() {
//        observable = FakeSettingsUiObservable()
//        navigation = FakeNavigation()
//        clear = FakeClear()
//        repository = FakeSettingsRepository()
//        runAsync = FakeRunAsync()
//        viewModel = SettingsViewModel(
//            repository = repository,
//            navigation = navigation,
//            clear = clear,
//            observable = observable,
//            runAsync = runAsync
//        )
//    }
//
//    @Test
//    fun happyPass() {
//        repository.hasFromCurrencies()
//        viewModel.init()
//        runAsync.returnLoadResult()
//        observable.checkUiSate(
//            expected = SettingsUiState.FirstChoice(
//                listOf(
//                    CurrencyChoiceUi.Base(currency = "USD", isSelected = false),
//                    CurrencyChoiceUi.Base(currency = "EUR", isSelected = false),
//                    CurrencyChoiceUi.Base(currency = "JPY", isSelected = false)
//                )
//            )
//        )
//
//        viewModel.chooseFirstCurrency(currency = "USD")
//        runAsync.returnLoadResult()
//        observable.checkUiSate(
//            expected = SettingsUiState.SecondChoice(
//                fromCurrency = listOf(
//                    CurrencyChoiceUi.Base(currency = "USD", isSelected = true),
//                    CurrencyChoiceUi.Base(currency = "EUR", isSelected = false),
//                    CurrencyChoiceUi.Base(currency = "JPY", isSelected = false)
//                ),
//                toCurrency = listOf(
//                    CurrencyChoiceUi.Base(currency = "EUR", isSelected = false),
//                    CurrencyChoiceUi.Base(currency = "JPY", isSelected = false)
//                )
//            )
//        )
//
//        viewModel.chooseSecondCurrency(from = "USD", to = "EUR")
//        observable.checkUiSate(
//            SettingsUiState.Save(
//                toCurrency = listOf(
//                    CurrencyChoiceUi.Base(currency = "EUR", isSelected = true),
//                    CurrencyChoiceUi.Base(currency = "JPY", isSelected = false)
//                )
//            )
//        )
//
//        viewModel.save(fromCurrency = "USD", toCurrency = "EUR")
//        repository.checkSavedSelectedPair(
//            listOf(
//                CurrencyPair(
//                    fromCurrency = "USD",
//                    toCurrency = "EUR"
//                )
//            )
//        )
//
//        navigation.checkNavigateToSettings()
//        clear.checkCalled(SettingsViewModel::class.java)
//    }
//}
//
//private class FakeSettingsRepository : SettingsRepository {
//
//    private var actualCurrencies = mutableListOf("USD", "EUR", "JPY")
//    private var actualSavedCurrencyPair = mutableListOf<CurrencyPair>()
//
//    override suspend fun allCurrencies(): List<String> {
//        return actualCurrencies
//    }
//
//    override suspend fun availableCurrenciesDestinations(from: String): List<String> {
//        val toCurrencies = mutableListOf<String>()
//        toCurrencies.clear()
//        toCurrencies.addAll(actualCurrencies)
//        toCurrencies.remove(from)
//        toCurrencies.removeAll(
//            actualSavedCurrencyPair.filter { it.fromCurrency == from }.map {
//                it.toCurrency
//            }
//        )
//        return toCurrencies
//    }
//
//    override suspend fun save(from: String, to: String) {
//        actualSavedCurrencyPair.add(CurrencyPair(from, to))
//    }
//
//    fun checkSavedSelectedPair(expected: List<CurrencyPair>) {
//        Assert.assertEquals(expected, actualSavedCurrencyPair)
//    }
//
//    fun hasFromCurrencies() {
//        actualCurrencies
//    }
//
//}
//
//private class FakeSettingsUiObservable : UiObservable<SettingsUiState> {
//
//    private var actualUiState: SettingsUiState = SettingsUiState.Empty
//    private var actualObserver: UpdateUi<SettingsUiState> = UpdateUi.Empty()
//
//    override fun updateObserver(observer: UpdateUi<SettingsUiState>) {
//        actualObserver = observer
//    }
//
//    override fun updateUi(uiState: SettingsUiState) {
//        actualUiState = uiState
//    }
//
//    fun checkUiSate(expected: SettingsUiState) {
//        Assert.assertEquals(expected, actualUiState)
//    }
//
//}