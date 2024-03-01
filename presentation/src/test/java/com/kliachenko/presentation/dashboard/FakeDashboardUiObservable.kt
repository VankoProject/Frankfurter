package com.kliachenko.presentation.dashboard

import com.kliachenko.presentation.core.UiObservable
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.dashboard.adapter.FavoritePairUi
import org.junit.Assert

class FakeDashboardUiObservable : UiObservable<DashboardUiState> {

    private var actualObserver: UpdateUi<DashboardUiState> = UpdateUi.Empty()
    private var actualUiState: DashboardUiState = DashboardUiState.Empty

    override fun updateObserver(observer: UpdateUi<DashboardUiState>) {
        this.actualObserver = observer
    }

    override fun updateUi(uiState: DashboardUiState) {
        actualUiState = uiState
    }

    fun checkProgress() {
        val expected: DashboardUiState = DashboardUiState.Progress
        Assert.assertEquals(expected, actualUiState)
    }

    fun checkEmpty() {
        val expected: DashboardUiState = DashboardUiState.Empty
        Assert.assertEquals(expected, actualUiState)
    }

    fun checkError() {
        val expected: DashboardUiState = DashboardUiState.Error(message = "Error")
        Assert.assertEquals(expected, actualUiState)
    }

    fun checkSuccess() {
        val expected: DashboardUiState =
            DashboardUiState.Base(listOf(FavoritePairUi.Base("USD/EUR", "1.0")))
        Assert.assertEquals(expected, actualUiState)
    }

    fun checkObserver(observer: UpdateUi<DashboardUiState>) {
        Assert.assertEquals(observer, actualObserver)
    }

    fun checkEmptyObserver(observer: UpdateUi<DashboardUiState>) {
        Assert.assertNotEquals(observer, actualObserver)
    }

    fun checkDashboardUiState(expected: DashboardUiState) {
        Assert.assertEquals(expected, actualUiState)
    }

}