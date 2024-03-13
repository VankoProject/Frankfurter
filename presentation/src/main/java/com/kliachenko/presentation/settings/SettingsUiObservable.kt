package com.kliachenko.presentation.settings

import com.kliachenko.presentation.core.UiObservable
import javax.inject.Inject

interface SettingsUiObservable : UiObservable<SettingsUiState> {

    class Base @Inject constructor():
        UiObservable.Abstract<SettingsUiState>(SettingsUiState.Initial), SettingsUiObservable
}