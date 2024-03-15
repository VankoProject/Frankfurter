package com.kliachenko.presentation.dashboard

import com.kliachenko.presentation.core.ShowList
import com.kliachenko.presentation.dashboard.adapter.FavoritePairUi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DashboardUiStateTest {

    private lateinit var showList: FakeShowList

    @Before
    fun setup() {
        showList = FakeShowList()
    }

    @Test
    fun testEmptyState() {
        val uiState: DashboardUiState = DashboardUiState.Empty
        uiState.update(showList)
        showList.checkShow(listOf(FavoritePairUi.Empty))
    }

    @Test
    fun testProgressState() {
        val uiState: DashboardUiState = DashboardUiState.Progress
        uiState.update(showList)
        showList.checkShow(listOf(FavoritePairUi.Progress))
    }

    @Test
    fun testErrorState() {
        val uiState: DashboardUiState = DashboardUiState.Error("No internet connection")
        uiState.update(showList)
        showList.checkShow(listOf(FavoritePairUi.Error("No internet connection")))
    }

    @Test
    fun testBaseState() {
        val uiState: DashboardUiState = DashboardUiState.Base(
            listOf(
                FavoritePairUi.Base("USD", "EUR"),
                FavoritePairUi.Base("USD", "JPY")
            )
        )
        uiState.update(showList)
        showList.checkShow(listOf(
            FavoritePairUi.Base("USD", "EUR"),
            FavoritePairUi.Base("USD", "JPY")
        ))
    }
}

private class FakeShowList : ShowList<FavoritePairUi> {
    var actual: List<FavoritePairUi> = emptyList()

    override fun show(adapter: List<FavoritePairUi>) {
        actual = adapter
    }

    fun checkShow(expected: List<FavoritePairUi>) {
        assertEquals(expected, actual)
    }
}