package com.kliachenko.frankfurter.core

import android.app.Application
import com.kliachenko.presentation.core.CustomViewModel

class App : Application(), ProvideViewModel {

    private lateinit var factory: ProvideViewModel.Factory

    override fun onCreate() {
        super.onCreate()
        val makeViewModel = ProvideViewModel.Base(this)
        factory = ProvideViewModel.Factory(makeViewModel)
    }

    override fun <T : CustomViewModel> viewModel(viewModelClass: Class<T>): T {
        return factory.viewModel(viewModelClass)
    }
}