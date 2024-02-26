package com.kliachenko.presentation.main

import com.kliachenko.presentation.core.CustomViewModel
import com.kliachenko.presentation.core.Screen
import com.kliachenko.presentation.core.UpdateUi

class MainViewModel(
    private val observable: UpdateUi<Screen>,
) : CustomViewModel