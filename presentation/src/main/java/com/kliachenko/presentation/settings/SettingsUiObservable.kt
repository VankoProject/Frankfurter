package com.kliachenko.presentation.settings

import com.kliachenko.presentation.core.UiObservable

interface SettingsUiObservable : UiObservable<SettingsUiState> {

    class Base :
        UiObservable.Abstract<SettingsUiState>(SettingsUiState.Empty), SettingsUiObservable
}