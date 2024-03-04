package com.kliachenko.presentation.dashboard

import com.kliachenko.domain.dashboard.DashBoardItem
import com.kliachenko.presentation.dashboard.adapter.FavoritePairUi
import kotlin.math.roundToInt

class BaseDashboardItemMapper : DashBoardItem.Mapper<FavoritePairUi> {

    override fun mapItems(fromCurrency: String, toCurrency: String, rate: Double): FavoritePairUi {
        return FavoritePairUi.Base(
            pair = "$fromCurrency/$toCurrency",
            rate = ((rate*100).roundToInt()/100.0).toString(),
        )
    }

}