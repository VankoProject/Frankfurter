package com.kliachenko.frankfurter.core

import android.app.Application
import com.kliachenko.frankfurter.core.module.ProvideInstance
import com.kliachenko.frankfurter.core.module.ProvideModule
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.core.CustomViewModel
import com.kliachenko.presentation.core.ProvideViewModel

abstract class App : Application(), ProvideViewModel {

    private lateinit var viewModelFactory: ProvideViewModel.Factory

    override fun onCreate() {
        super.onCreate()
        val clear = object : Clear {
            override fun clear(clazz: Class<out CustomViewModel>) {
                viewModelFactory.clear(clazz)
            }
        }

        val makeViewModel: ProvideViewModel =
            BaseProvideViewModel(
                ProvideModule.Base(
                    core = Core.Base(this),
                    provideInstance = provideInstance(),
                    clear = clear
                )
            )

        viewModelFactory = ProvideViewModel.Factory(makeViewModel)

    }

    abstract fun provideInstance(): ProvideInstance

    override fun <T : CustomViewModel> viewModel(viewModelClass: Class<T>): T {
        return viewModelFactory.viewModel(viewModelClass)
    }
}

class Release : App() {

    override fun provideInstance(): ProvideInstance = ProvideInstance.Base()

}

class Mock : App() {

    override fun provideInstance(): ProvideInstance = ProvideInstance.Mock()

}