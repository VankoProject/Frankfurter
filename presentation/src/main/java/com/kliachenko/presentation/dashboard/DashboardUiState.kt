package com.kliachenko.presentation.dashboard

import com.kliachenko.presentation.core.ShowList
import com.kliachenko.presentation.dashboard.adapter.FavoritePairUi

interface DashboardUiState {

    fun update(adapter: ShowList<FavoritePairUi>)

    abstract class Abstract(private val favoritePairUi: FavoritePairUi) : DashboardUiState {
        override fun update(adapter: ShowList<FavoritePairUi>) {
            adapter.show(listOf(favoritePairUi))
        }
    }

    object Empty : Abstract(FavoritePairUi.Empty)

    object Progress : Abstract(FavoritePairUi.Progress)

    data class Error(private val message: String) : Abstract(FavoritePairUi.Error(message))

    data class Base(private val currencyList: List<FavoritePairUi>) : DashboardUiState {
        override fun update(adapter: ShowList<FavoritePairUi>) {
            adapter.show(currencyList)
        }
    }

}
