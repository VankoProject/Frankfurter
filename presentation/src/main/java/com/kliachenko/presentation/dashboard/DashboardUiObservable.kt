package com.kliachenko.presentation.dashboard

import com.kliachenko.presentation.core.UiObservable

interface DashboardUiObservable : UiObservable<DashboardUiState> {

    class Base :
        UiObservable.Abstract<DashboardUiState>(DashboardUiState.Empty), DashboardUiObservable
}