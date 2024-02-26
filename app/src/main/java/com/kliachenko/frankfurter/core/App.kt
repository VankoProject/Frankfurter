package com.kliachenko.frankfurter.core

import android.app.Application
import com.kliachenko.frankfurter.core.modules.ProvideModule
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.core.CustomViewModel
import com.kliachenko.presentation.core.ProvideViewModel

class App : Application(), ProvideViewModel {

    private lateinit var factory: ProvideViewModel.Factory

    override fun onCreate() {
        super.onCreate()
        val clear = object : Clear {
            override fun clear(clazz: Class<out CustomViewModel>) {
                factory.clear(clazz)
            }
        }
        factory = ProvideViewModel.Factory(
            BaseProvideViewModel(
                ProvideModule.Base(
                    core = Core.Base(this),
                    provideInstance = ProvideInstance.Base(),
                    clear = clear
                )
            )
        )
    }

    override fun <T : CustomViewModel> viewModel(viewModelClass: Class<T>): T {
        return factory.viewModel(viewModelClass)
    }

}