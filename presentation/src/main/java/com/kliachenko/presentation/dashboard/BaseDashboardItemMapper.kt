package com.kliachenko.presentation.dashboard

import com.kliachenko.domain.dashboard.DashBoardItem
import com.kliachenko.presentation.core.Delimiter
import com.kliachenko.presentation.dashboard.adapter.FavoritePairUi
import javax.inject.Inject
import kotlin.math.roundToInt

class BaseDashboardItemMapper @Inject constructor(
    private val delimiter: Delimiter
) : DashBoardItem.Mapper<FavoritePairUi> {

    override fun mapItems(fromCurrency: String, toCurrency: String, rate: Double): FavoritePairUi {
        return FavoritePairUi.Base(
            pair = delimiter.concat(fromCurrency, toCurrency),
            rate = ((rate*100).roundToInt()/100.0).toString(),
        )
    }

}