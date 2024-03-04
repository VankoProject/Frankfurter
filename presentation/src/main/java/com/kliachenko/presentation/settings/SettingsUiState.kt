package com.kliachenko.presentation.settings

import com.kliachenko.presentation.core.views.ChangeVisibility
import com.kliachenko.presentation.settings.adapter.CurrencyChoiceUi
import com.kliachenko.presentation.settings.adapter.SettingsAdapter

interface SettingsUiState {

    fun update(
        fromCurrencyAdapter: SettingsAdapter,
        toCurrencyAdapter: SettingsAdapter,
    )

    fun update(saveButton: ChangeVisibility) = saveButton.hide()

    data class FirstChoice(
        private val fromCurrency: List<CurrencyChoiceUi>,
    ) : SettingsUiState {
        override fun update(
            fromCurrencyAdapter: SettingsAdapter,
            toCurrencyAdapter: SettingsAdapter,
        ) {
            fromCurrencyAdapter.show(fromCurrency)
            toCurrencyAdapter.show(emptyList())
        }
    }

    data class SecondChoice(
        private val toCurrency: List<CurrencyChoiceUi>,
        private val fromCurrency: List<CurrencyChoiceUi>,
    ) : SettingsUiState {
        override fun update(
            fromCurrencyAdapter: SettingsAdapter,
            toCurrencyAdapter: SettingsAdapter,
        ) {
            fromCurrencyAdapter.show(fromCurrency)
            toCurrencyAdapter.show(toCurrency)
        }
    }

    data class Save(
        private val toCurrency: List<CurrencyChoiceUi>
    ) : SettingsUiState {
        override fun update(
            fromCurrencyAdapter: SettingsAdapter,
            toCurrencyAdapter: SettingsAdapter,
        ) {
            toCurrencyAdapter.show(toCurrency)
        }

        override fun update(saveButton: ChangeVisibility) {
            saveButton.show()
        }
    }

    object Initial: SettingsUiState {
        override fun update(
            fromCurrencyAdapter: SettingsAdapter,
            toCurrencyAdapter: SettingsAdapter,
        ) = Unit
    }

}
