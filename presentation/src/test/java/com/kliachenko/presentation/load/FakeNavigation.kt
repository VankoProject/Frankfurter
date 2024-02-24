package com.kliachenko.presentation.load

import org.junit.Assert

class FakeNavigation: Navigation() {

    private var actualScreen: Screen = Screen.Empty

    override fun updateUi(uiState: Screen) {
        actualScreen = uiState
    }

    fun checkNavigateToDashBoardScreen() {
        val expected: Screen = DashBoardScreen.Initial
        Assert.assertEquals(expected, actualScreen)
    }
}