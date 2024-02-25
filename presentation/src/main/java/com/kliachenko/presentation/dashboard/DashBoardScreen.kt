package com.kliachenko.presentation.dashboard

import com.kliachenko.presentation.core.Screen

interface DashBoardScreen {

    object Initial: Screen.Replace(DashBoardFragment::class.java)
}
