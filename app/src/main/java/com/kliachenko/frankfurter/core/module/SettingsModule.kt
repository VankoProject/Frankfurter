package com.kliachenko.frankfurter.core.module

import com.kliachenko.data.dashboard.cache.FavoritePairCacheDataSource
import com.kliachenko.data.loading.cache.CurrencyCacheDataSource
import com.kliachenko.frankfurter.core.Core
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.settings.SettingsUiObservable
import com.kliachenko.presentation.settings.SettingsViewModel

class SettingsModule(
    private val core: Core,
    private val clear: Clear,
    private val provideInstance: ProvideSettingsRepository,
) : Module<SettingsViewModel> {

    override fun viewModel(): SettingsViewModel {
        val observable = SettingsUiObservable.Base()
        return SettingsViewModel(
            navigation = core.provideNavigation(),
            clear = clear,
            observable = observable,
            runAsync = core.provideRunAsync(),
            repository = provideInstance.provideSettingsRepository(
                cacheDataSource = CurrencyCacheDataSource.Base(
                    core.provideCurrencyDataBase().dataBase().currencyDao()
                ),
                favoritePairCacheDataSource = FavoritePairCacheDataSource.Base(
                    core.provideCurrencyDataBase().dataBase().currencyPairDao()
                )
            )
        )
    }
}