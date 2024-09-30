package com.kliachenko.presentation.dashboard

import com.kliachenko.presentation.core.UiObservable
import javax.inject.Inject

interface DashboardUiObservable : UiObservable<DashboardUiState> {

    class Base @Inject constructor():
        UiObservable.Abstract<DashboardUiState>(DashboardUiState.Empty), DashboardUiObservable
}