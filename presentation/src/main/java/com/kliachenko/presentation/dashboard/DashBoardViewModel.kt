package com.kliachenko.presentation.dashboard

import com.kliachenko.domain.dashboard.DashboardRepository
import com.kliachenko.domain.dashboard.DashboardResult
import com.kliachenko.presentation.core.BaseViewModel
import com.kliachenko.presentation.core.Delimiter
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.RunAsync
import com.kliachenko.presentation.settings.SettingsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val observable: DashboardUiObservable,
    private val navigation: Navigation,
    private val repository: DashboardRepository,
    private val delimiter: Delimiter,
    runAsync: RunAsync,
    private val mapper: DashboardResult.Mapper,
) : BaseViewModel<DashboardUiState>(observable, runAsync), ClickActions {

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

    override fun remove(itemId: String) {
        observable.updateUi(DashboardUiState.Progress)
        runAsync({
            val split = delimiter.split(itemId)
            repository.removeItem(split[0], split[1])
        }) { result ->
            result.map(mapper)
        }
    }

    fun openSettings() {
        navigation.updateUi(SettingsScreen.Initial)
    }

}
