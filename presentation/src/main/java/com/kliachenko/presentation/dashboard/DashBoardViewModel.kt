package com.kliachenko.presentation.dashboard

import com.kliachenko.domain.dashboard.DashboardRepository
import com.kliachenko.domain.dashboard.DashboardResult
import com.kliachenko.presentation.core.BaseViewModel
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.RunAsync
import com.kliachenko.presentation.core.UiObservable
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.settings.SettingsScreen

class DashBoardViewModel(
    private val observable: UiObservable<DashboardUiState>,
    private val navigation: Navigation,
    private val repository: DashboardRepository,
    runAsync: RunAsync,
    private val clear: Clear,
    private val mapper: DashboardResult.Mapper = BaseDashboardResultMapper(observable),
) : BaseViewModel(runAsync), ClickActions {

    fun load() {
        observable.updateUi(DashboardUiState.Progress)
        runAsync({
            repository.dashboardItems()
        }) { result ->
            result.map(mapper)
        }
    }

    override fun retry() {
        load()
    }

    fun openSettings() {
        navigation.updateUi(SettingsScreen.Initial)
        clear.clear(DashBoardViewModel::class.java)
    }

    fun startGettingUpdates(observer: UpdateUi<DashboardUiState>) {
        observable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UpdateUi.Empty())
    }

}
