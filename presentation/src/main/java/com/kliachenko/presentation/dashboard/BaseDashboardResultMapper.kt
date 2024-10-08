package com.kliachenko.presentation.dashboard

import com.kliachenko.domain.dashboard.DashBoardItem
import com.kliachenko.domain.dashboard.DashboardResult
import com.kliachenko.presentation.dashboard.adapter.FavoritePairUi
import javax.inject.Inject

class BaseDashboardResultMapper @Inject constructor(
    private val observable: DashboardUiObservable,
    private val itemMapper: DashBoardItem.Mapper<FavoritePairUi>,
) : DashboardResult.Mapper {

    override fun mapSuccess(items: List<DashBoardItem>) {
        observable.updateUi(DashboardUiState.Base(items.map {
            it.map(itemMapper)
        }))
    }

    override fun mapError(message: String) {
        observable.updateUi(DashboardUiState.Error(message = message))
    }

    override fun mapEmpty() {
        observable.updateUi(DashboardUiState.Empty)
    }

}
