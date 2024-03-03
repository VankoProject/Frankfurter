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

    object Empty : SettingsUiState {
        override fun update(
            fromCurrencyAdapter: SettingsAdapter,
            toCurrencyAdapter: SettingsAdapter,
        ) {
            fromCurrencyAdapter.show(listOf(CurrencyChoiceUi.Empty))
            toCurrencyAdapter.show(listOf(CurrencyChoiceUi.Empty))
        }
    }

    data class FromCurrency(
        private val fromCurrency: List<CurrencyChoiceUi>,
    ) : SettingsUiState {
        override fun update(
            fromCurrencyAdapter: SettingsAdapter,
            toCurrencyAdapter: SettingsAdapter,
        ) {
            fromCurrencyAdapter.show(fromCurrency)
        }
    }

    data class ToCurrency(
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
        private val toCurrency: List<CurrencyChoiceUi>,
        private val saveButton: ChangeVisibility,
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

}
