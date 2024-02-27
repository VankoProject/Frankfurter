package com.kliachenko.frankfurter.core.module

import com.kliachenko.presentation.core.CustomViewModel

interface Module<T : CustomViewModel> {

    fun viewModel(): T
}