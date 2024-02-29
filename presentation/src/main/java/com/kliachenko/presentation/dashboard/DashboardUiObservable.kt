package com.kliachenko.presentation.dashboard

import com.kliachenko.presentation.core.UiObservable

interface DashboardUiObservable: UiObservable<SettingsUiState> {

    class Base: UiObservable.Abstract<SettingsUiState>(SettingsUiState.Empty), DashboardUiObservable
}