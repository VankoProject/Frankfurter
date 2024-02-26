package com.kliachenko.frankfurter.core

import com.kliachenko.frankfurter.core.modules.ProvideModule
import com.kliachenko.presentation.core.CustomViewModel
import com.kliachenko.presentation.core.ProvideViewModel

class BaseProvideViewModel(private val provideModule: ProvideModule) : ProvideViewModel {
    override fun <T : CustomViewModel> viewModel(viewModelClass: Class<T>): T {
        return provideModule.module(viewModelClass).viewModel()
    }
}