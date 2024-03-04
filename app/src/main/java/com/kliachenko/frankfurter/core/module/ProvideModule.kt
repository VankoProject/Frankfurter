package com.kliachenko.frankfurter.core.module

import com.kliachenko.frankfurter.core.Core
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.core.CustomViewModel
import com.kliachenko.presentation.dashboard.DashBoardViewModel
import com.kliachenko.presentation.loading.LoadViewModel
import com.kliachenko.presentation.main.MainViewModel
import com.kliachenko.presentation.settings.SettingsViewModel

interface ProvideModule {

    fun <T : CustomViewModel> module(clazz: Class<T>): Module<T>

    class Base(
        private val core: Core,
        private val provideInstance: ProvideInstance,
        private val clear: Clear,
    ) : ProvideModule {
        override fun <T : CustomViewModel> module(clazz: Class<T>): Module<T> {
            return when (clazz) {
                MainViewModel::class.java -> MainModule(core = core)
                LoadViewModel::class.java -> LoadModule(
                    core = core,
                    provideInstance = provideInstance as ProvideInstance.ProvideLoadRepository,
                    clear = clear
                )

                DashBoardViewModel::class.java -> DashboardModule(
                    core = core,
                    clear = clear,
                    provideInstance = provideInstance as ProvideInstance.ProvideDashBoardRepository
                )

                SettingsViewModel::class.java -> SettingsModule(
                    core = core,
                    clear = clear,
                    provideInstance = provideInstance as ProvideInstance.ProvideSettingsRepository
                )

                else -> throw IllegalStateException("unknown module $clazz")
            } as Module<T>
        }
    }
}