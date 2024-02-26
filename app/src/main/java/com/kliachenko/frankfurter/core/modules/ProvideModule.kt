package com.kliachenko.frankfurter.core.modules

import com.kliachenko.frankfurter.core.Core
import com.kliachenko.frankfurter.core.ProvideInstance
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.core.CustomViewModel
import com.kliachenko.presentation.loading.LoadViewModel
import com.kliachenko.presentation.main.MainViewModel

interface ProvideModule {

    fun <T : CustomViewModel> module(clazz: Class<T>): Module<T>

    class Base(
        private val core: Core,
        private val provideInstance: ProvideInstance,
        private val clear: Clear,
    ) : ProvideModule {
        override fun <T : CustomViewModel> module(clazz: Class<T>): Module<T> {
            return when (clazz) {
                LoadViewModel::class.java -> LoadModule(core, provideInstance, clear)
                MainViewModel::class.java -> MainModule(core)
                else -> throw IllegalStateException("unknown viewModel $clazz")
            } as Module<T>
        }
    }

}