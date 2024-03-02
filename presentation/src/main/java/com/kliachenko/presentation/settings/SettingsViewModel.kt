package com.kliachenko.presentation.settings

import com.kliachenko.domain.settings.SettingsRepository
import com.kliachenko.presentation.core.BaseViewModel
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.RunAsync
import com.kliachenko.presentation.core.UiObservable
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.dashboard.DashBoardScreen

class SettingsViewModel(
    private val repository: SettingsRepository,
    private val navigation: Navigation,
    private val clear: Clear,
    private val observable: UiObservable<SettingsUiState>,
    runAsync: RunAsync,
) : BaseViewModel(runAsync) {

    fun init() {
        // TODO:
    }

    fun save() {
        observable.updateUi(SettingsUiState.Progress)
        runAsync({
            repository.save(from = "", to = "")
        }) {
            it //todo
        }
        navigation.updateUi(DashBoardScreen)
        clear.clear(SettingsViewModel::class.java)
    }

    fun startGettingUpdates(observer: UpdateUi<SettingsUiState>) {
        observable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UpdateUi.Empty())
    }

}
