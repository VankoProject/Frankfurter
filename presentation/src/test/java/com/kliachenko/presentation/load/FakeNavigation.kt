package com.kliachenko.presentation.load

import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.Screen
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.dashboard.DashBoardScreen
import org.junit.Assert

class FakeNavigation: Navigation {

    private var actualScreen: Screen = Screen.Empty
    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun updateUi(uiState: Screen) {
        actualScreen = uiState
    }

    override fun updateObserver(observer: UpdateUi<Screen>) {
        TODO("Not yet implemented")
    }

    fun checkNavigateToDashBoardScreen() {
        val expected: Screen = DashBoardScreen.Initial
        Assert.assertEquals(expected, actualScreen)
    }
}