//package com.kliachenko.frankfurter.old
//
//import com.kliachenko.data.dashboard.cache.currencyPair.FavoritePairCacheDataSource
//import com.kliachenko.data.dashboard.cache.currencyCache.CurrencyCacheDataSource
//import com.kliachenko.data.settings.BaseSettingsRepository
//import com.kliachenko.domain.settings.SettingsInteractor
//import com.kliachenko.frankfurter.core.Core
//import com.kliachenko.frankfurter.core.ProvideInstance
//import com.kliachenko.presentation.settings.SettingsUiObservable
//import com.kliachenko.presentation.settings.SettingsViewModel
//
//class SettingsModule(
//    private val core: Core,
//    private val clear: Clear,
//    private val provideInstance: ProvideInstance
//) : Module<SettingsViewModel> {
//
//    override fun viewModel(): SettingsViewModel {
//        val observable = SettingsUiObservable.Base()
//        return SettingsViewModel(
//            navigation = core.provideNavigation(),
//            clear = clear,
//            observable = observable,
//            runAsync = core.provideRunAsync(),
//            interactor = SettingsInteractor.Base(
//                settingsRepository = BaseSettingsRepository(
//                    currencyCacheDataSource = CurrencyCacheDataSource.Base(
//                        core.provideCurrencyDataBase().dataBase().currencyDao()
//                    ),
//                    favoritePairCacheDataSource = FavoritePairCacheDataSource.Base(
//                        core.provideCurrencyDataBase().dataBase().currencyPairDao()
//                    )
//                ),
//                freeCountPair = provideInstance.provideFreeCountPair(),
//                premiumUserStorage = core.providePremiumUserStorage()
//            )
//        )
//    }
//}