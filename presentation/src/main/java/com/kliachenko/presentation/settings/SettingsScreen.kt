package com.kliachenko.presentation.settings

import com.kliachenko.presentation.core.Screen

interface SettingsScreen {

    object Initial : Screen.Replace(SettingsFragment::class.java)
}