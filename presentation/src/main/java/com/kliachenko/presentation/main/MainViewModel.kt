package com.kliachenko.presentation.main

import com.kliachenko.presentation.core.CustomViewModel
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.Screen
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.loading.LoadScreen

class MainViewModel(
    private val navigation: Navigation,
) : CustomViewModel {

    fun init(firstRun: Boolean) {
        if(firstRun) {
            navigation.updateUi(LoadScreen)
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