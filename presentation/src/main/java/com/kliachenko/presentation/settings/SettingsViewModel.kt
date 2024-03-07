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

    fun init(bundleWrapper: BundleWrapper.Mutable) {
        if (bundleWrapper.isEmpty()) {
            runAsync({
                repository.allCurrencies()
            }) { currencies ->
                val listCurrencies =
                    currencies.map { CurrencyChoiceUi.Base(isSelected = false, currency = it) }
                observable.updateUi(
                    SettingsUiState.FirstChoice(fromCurrency = listCurrencies)
                )
            }
        } else {
            bundleWrapper.restore().also { (from, to) ->
                if (from.isNotEmpty() && to.isEmpty()) chooseFirstCurrency(from)
                else if (from.isNotEmpty() && to.isNotEmpty()) chooseSecondCurrency(
                    from,
                    to
                )
            }
        }
    }

    fun backDashBoard() {
        navigation.updateUi(DashBoardScreen)
        clear.clear(SettingsViewModel::class.java)
    }

    override fun chooseFirstCurrency(currency: String) {
        runAsync({
            val fromCurrencies = repository.allCurrencies().map {
                CurrencyChoiceUi.Base(isSelected = it == currency, currency = it)
            }
            val toCurrencies = repository.availableCurrenciesDestinations(currency)
                .map {
                    CurrencyChoiceUi.Base(isSelected = false, currency = it)
                }
                .let { it.ifEmpty { listOf(CurrencyChoiceUi.Empty) } }

            SettingsUiState.SecondChoice(fromCurrency = fromCurrencies, toCurrency = toCurrencies)

        }) {
            observable.updateUi(it)
        }
    }

    override fun chooseSecondCurrency(from: String, to: String) {
        runAsync({
            val toCurrencies = repository.availableCurrenciesDestinations(from)
            SettingsUiState.Save(toCurrency = toCurrencies.map {
                CurrencyChoiceUi.Base(isSelected = it == to, currency = it)
            },
                fromCurrency = repository.allCurrencies().map {
                    CurrencyChoiceUi.Base(isSelected = it == from, currency = it)
                })
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
