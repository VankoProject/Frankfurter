package com.kliachenko.presentation.dashboard

import com.kliachenko.domain.dashboard.DashBoardItem
import com.kliachenko.presentation.dashboard.adapter.FavoritePairUi
import org.junit.Before
import org.junit.Test

class BaseDashboardResultMapperTest {

    private lateinit var observable: FakeDashboardUiObservable
    private lateinit var resultMapper: BaseDashboardResultMapper
    private lateinit var itemMapper: FakeBaseDashboardItemMapper

    @Before
    fun setup() {
        observable = FakeDashboardUiObservable()
        itemMapper = FakeBaseDashboardItemMapper()
        resultMapper = BaseDashboardResultMapper(
            observable = observable, itemMapper = itemMapper
        )
    }

    @Test
    fun mapSuccess() {
        val items = listOf(DashBoardItem.Base("USD", "EUR", 1.0))
        resultMapper.mapSuccess(items)
        val expected = DashboardUiState.Base(
            listOf(FavoritePairUi.Base("USD/EUR", "1.0")))
        observable.checkDashboardUiState(expected)
    }

    @Test
    fun mapError() {
        val error = "Error"
        resultMapper.mapError(error)
        val expected = DashboardUiState.Error(message = error)
        observable.checkDashboardUiState(expected)
    }

    @Test
    fun mapEmpty() {
        resultMapper.mapEmpty()
        val expected = DashboardUiState.Empty
        observable.checkDashboardUiState(expected)
    }
}

private class FakeBaseDashboardItemMapper: DashBoardItem.Mapper<FavoritePairUi> {

    override fun mapItems(fromCurrency: String, toCurrency: String, rate: Double): FavoritePairUi {
        return FavoritePairUi.Base(
            pair = "$fromCurrency/$toCurrency",
            rate = rate.toString()
        )
    }

}
