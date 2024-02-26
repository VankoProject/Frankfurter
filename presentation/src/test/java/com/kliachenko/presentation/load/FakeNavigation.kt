package com.kliachenko.presentation.load

import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.Screen
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.dashboard.DashBoardScreen
import org.junit.Assert

class FakeNavigation: Navigation {

    private var actualScreen: Screen = Screen.Empty
    private var observer: UpdateUi<Screen> = UpdateUi.Empty()
    override fun clear() {
        // TODO:  
    }

    override fun updateUi(uiState: Screen) {
        actualScreen = uiState
    }

    override fun updateObserver(observer: UpdateUi<Screen>) {
        this.observer = observer
    }

    fun checkNavigateToDashBoardScreen() {
        val expected: Screen = DashBoardScreen.Initial
        Assert.assertEquals(expected, actualScreen)
    }
}