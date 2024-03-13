//package com.kliachenko.frankfurter.old
//
//import com.kliachenko.data.loading.BaseLoadCurrencyRepository
//import com.kliachenko.data.loading.cache.CurrencyCacheDataSource
//import com.kliachenko.frankfurter.core.Core
//import com.kliachenko.frankfurter.core.ProvideInstance
//import com.kliachenko.presentation.loading.BaseLoadResultMapper
//import com.kliachenko.presentation.loading.LoadUiObservable
//import com.kliachenko.presentation.loading.LoadViewModel
//
//class LoadModule(
//    private val core: Core,
//    private val provideInstance: ProvideInstance,
//    private val clear: Clear,
//) : Module<LoadViewModel> {
//
//    override fun viewModel(): LoadViewModel {
//        val observable = LoadUiObservable.Base()
//        return LoadViewModel(
//            observable = observable,
//            repository = BaseLoadCurrencyRepository(
//                loadCurrencyCloudDataSource = provideInstance.provideLoadCloudDataSource(core.provideRetrofit()),
//                currencyCacheDataSource = CurrencyCacheDataSource.Base(
//                    core.provideCurrencyDataBase().dataBase().currencyDao()
//                ),
//                provideResources = core.provideResources()
//            ),
//            runAsync = core.provideRunAsync(),
//            mapper = BaseLoadResultMapper(
//                observable = observable,
//                core.provideNavigation(),
//                clear
//            )
//        )
//    }
//}