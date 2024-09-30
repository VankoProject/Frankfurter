package com.kliachenko.presentation.main

import androidx.lifecycle.ViewModel
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.Screen
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.dashboard.DashBoardScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigation: Navigation,
) : ViewModel() {

    fun init(firstRun: Boolean) {
        if (firstRun) {
            navigation.updateUi(DashBoardScreen)
        }
    }

    fun startGettingUpdates(observer: UpdateUi<Screen>) {
        navigation.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        navigation.updateObserver(UpdateUi.Empty())
    }

    fun notifyObserved() {
        navigation.clear()
    }
}