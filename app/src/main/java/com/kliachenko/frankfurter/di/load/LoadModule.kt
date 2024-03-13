package com.kliachenko.frankfurter.di.load

import com.kliachenko.data.loading.BaseLoadCurrencyRepository
import com.kliachenko.domain.load.LoadCurrenciesRepository
import com.kliachenko.domain.load.LoadCurrenciesResult
import com.kliachenko.presentation.loading.BaseLoadResultMapper
import com.kliachenko.presentation.loading.LoadUiObservable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class LoadModule {

    @Binds
    @ViewModelScoped
    abstract fun bindLoadObservable(
        observable: LoadUiObservable.Base,
    ): LoadUiObservable

    @Binds
    abstract fun bindLoadCurrencyResultMapper(mapper: BaseLoadResultMapper): LoadCurrenciesResult.Mapper

    @Binds
    abstract fun bindLoadRepository(loadCurrenciesRepository: BaseLoadCurrencyRepository): LoadCurrenciesRepository

}