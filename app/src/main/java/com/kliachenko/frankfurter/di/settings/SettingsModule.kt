package com.kliachenko.frankfurter.di.settings

import com.kliachenko.data.settings.BasePremiumUserStorage
import com.kliachenko.data.settings.BaseSettingsRepository
import com.kliachenko.domain.settings.PremiumUserStorage
import com.kliachenko.domain.settings.SaveResult
import com.kliachenko.domain.settings.SettingsInteractor
import com.kliachenko.domain.settings.SettingsRepository
import com.kliachenko.presentation.settings.BaseSaveResultMapper
import com.kliachenko.presentation.settings.SettingsUiObservable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    abstract fun bindSettingUiObservable(
        observable: SettingsUiObservable.Base,
    ): SettingsUiObservable

    @Binds
    abstract fun bindSaveResultMapper(mapper: BaseSaveResultMapper): SaveResult.Mapper

    @Binds
    @Singleton
    abstract fun bindPremiumUserStorageSave(premiumUserStorage: PremiumUserStorage.Mutable): PremiumUserStorage.Save

    @Binds
    @Singleton
    abstract fun bindPremiumUserStorageRead(premiumUserStorage: PremiumUserStorage.Mutable): PremiumUserStorage.Read

    @Binds
    @Singleton
    abstract fun bindBasePremiumUserStorage(premiumUserStorage: BasePremiumUserStorage): PremiumUserStorage.Mutable

    @Binds
    @Singleton
    abstract fun bindSettingRepository(settingsRepository: BaseSettingsRepository): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindInteractor(interactor: SettingsInteractor.Base): SettingsInteractor

}