package com.kliachenko.frankfurter.core.modules

import com.kliachenko.presentation.core.CustomViewModel

interface Module<T: CustomViewModel> {

    fun viewModel(): T
}