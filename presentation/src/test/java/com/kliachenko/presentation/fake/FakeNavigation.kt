package com.kliachenko.presentation.fake

import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.Screen
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.dashboard.DashBoardScreen
import com.kliachenko.presentation.loading.LoadScreen
import com.kliachenko.presentation.settings.SettingsScreen
import com.kliachenko.presentation.subscritpion.SubscriptionScreen
import org.junit.Assert.assertEquals

class FakeNavigation : Navigation {

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
        val expected: Screen = DashBoardScreen
        assertEquals(expected, actualScreen)
    }

    fun checkNavigateToLoadScreen() {
        val expected: Screen = LoadScreen
        assertEquals(expected, actualScreen)
    }

    fun checkNotCalled() {
        assertEquals(actualScreen, Screen.Empty)
    }

    fun checkObserver(expected: UpdateUi<Screen>) {
        assertEquals(expected, actualScreen)
    }

    fun checkEmpty(expected: UpdateUi<Screen>) {
        assertEquals(expected, Screen.Empty)
    }

    fun checkNavigateToSettings() {
        val expected: Screen = SettingsScreen.Initial
        assertEquals(expected, actualScreen)
    }

    fun checkPopBackStack() {
        val expected: Screen = Screen.Pop
        assertEquals(expected, actualScreen)
    }

    fun checkNavigateToSubscriptionScreen() {
        val expected: Screen = SubscriptionScreen
        assertEquals(expected, actualScreen)
    }

}