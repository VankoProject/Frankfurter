package com.kliachenko.frankfurter.core

import android.content.Context
import com.kliachenko.data.cache.CacheDataSource
import com.kliachenko.data.main.BaseMainRepository
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.core.CustomViewModel
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.RunAsync
import com.kliachenko.presentation.loading.LoadUiObservable
import com.kliachenko.presentation.loading.LoadViewModel

interface ProvideViewModel {

    fun <T : CustomViewModel> viewModel(viewModelClass: Class<T>): T

    class Factory(private val makeViewModel: ProvideViewModel) : ProvideViewModel, Clear {

        private val map = HashMap<Class<out CustomViewModel>, CustomViewModel>()

        override fun <T : CustomViewModel> viewModel(viewModelClass: Class<T>): T =
            if (map.containsKey(viewModelClass)) {
                map[viewModelClass]
            } else {
                val viewModel = makeViewModel.viewModel(viewModelClass)
                map[viewModelClass] = viewModel
                viewModel
            } as T

        override fun clear(clazz: Class<out CustomViewModel>) {
            map.remove(clazz)
        }
    }

    class Base(context: Context) : ProvideViewModel {

        private val observable = LoadUiObservable.Base()
        private val cacheDataSource = CacheDataSource.Base(context)
        private val repository = BaseMainRepository(cacheDataSource)
        private val navigation = Navigation.Base()
        private val runAsync = RunAsync.Base()


        override fun <T : CustomViewModel> viewModel(viewModelClass: Class<T>): T {
            return when (viewModelClass) {
                LoadViewModel::class.java -> LoadViewModel(
                    observable = observable,
                    repository = repository,
                    navigation = navigation,
                    runAsync = runAsync,
                )

                else -> throw IllegalStateException("No such viewModel with class:$viewModelClass")
            } as T
        }

    }
}