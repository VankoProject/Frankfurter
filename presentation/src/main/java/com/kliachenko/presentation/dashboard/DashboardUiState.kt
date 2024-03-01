package com.kliachenko.presentation.dashboard

import com.kliachenko.presentation.dashboard.adapter.FavoritePairUi
import com.kliachenko.presentation.dashboard.adapter.ShowList

interface DashboardUiState {

    fun update(adapter: ShowList)

    abstract class Abstract(private val favoritePairUi: FavoritePairUi) : DashboardUiState {
        override fun update(adapter: ShowList) {
            adapter.show(listOf(favoritePairUi))
        }
    }

    object Empty : Abstract(FavoritePairUi.Empty)

    object Progress : Abstract(FavoritePairUi.Progress)

    data class Error(private val message: String) : Abstract(FavoritePairUi.Error(message))

    data class Base(private val currencyList: List<FavoritePairUi>) : DashboardUiState {
        override fun update(adapter: ShowList) {
            adapter.show(currencyList)
        }
    }

}
