package com.kliachenko.presentation.settings

import com.kliachenko.domain.settings.SettingsRepository
import com.kliachenko.presentation.core.BaseViewModel
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.RunAsync
import com.kliachenko.presentation.core.UiObservable
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.dashboard.DashBoardScreen
import com.kliachenko.presentation.settings.adapter.CurrencyChoiceUi

class SettingsViewModel(
    private val repository: SettingsRepository,
    private val navigation: Navigation,
    private val clear: Clear,
    private val observable: UiObservable<SettingsUiState>,
    runAsync: RunAsync,
) : BaseViewModel(runAsync), ChooseCurrency {

    fun init() {
        runAsync({
            repository.allCurrencies()
        }) { currencies ->
            val listCurrencies =
                currencies.map { CurrencyChoiceUi.Base(currency = it, isSelected = false) }
            observable.updateUi(
                SettingsUiState.FromCurrency(listCurrencies)
            )
        }
    }

    fun backDashBoard() {
        navigation.updateUi(DashBoardScreen)
        clear.clear(SettingsViewModel::class.java)
    }

    override fun chooseCurrency(currency: String) {
        runAsync({
            SettingsUiState.ToCurrency(
                fromCurrency = repository.allCurrencies().map {
                    CurrencyChoiceUi.Base(isSelected = it == currency, currency = it)
                },
                toCurrency = repository.availableCurrenciesDestinations(currency).map {
                    CurrencyChoiceUi.Base(isSelected = false, currency = it)
                }
            )
        }) {
            observable.updateUi(it)
        }
    }

    fun save(fromCurrency: String, toCurrency: String) {
        runAsync({
            repository.save(from = fromCurrency, to = toCurrency)
        }) {
            backDashBoard()
        }
    }

    fun startGettingUpdates(observer: UpdateUi<SettingsUiState>) {
        observable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UpdateUi.Empty())
    }

}
