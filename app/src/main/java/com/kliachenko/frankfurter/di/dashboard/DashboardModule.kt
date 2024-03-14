package com.kliachenko.frankfurter.di.dashboard

import com.kliachenko.data.dashboard.BaseDashboardRepository
import com.kliachenko.data.dashboard.DashBoardItemsDataSource
import com.kliachenko.domain.dashboard.DashBoardItem
import com.kliachenko.domain.dashboard.DashboardRepository
import com.kliachenko.domain.dashboard.DashboardResult
import com.kliachenko.presentation.dashboard.BaseDashboardItemMapper
import com.kliachenko.presentation.dashboard.BaseDashboardResultMapper
import com.kliachenko.presentation.dashboard.DashboardUiObservable
import com.kliachenko.presentation.dashboard.adapter.FavoritePairUi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DashboardModule {

    @Binds
    @ViewModelScoped
    abstract fun bindDashboardObservable(
        observable: DashboardUiObservable.Base,
    ): DashboardUiObservable

    @Binds
    abstract fun bindDashboardResultMapper(
        mapper: BaseDashboardResultMapper): DashboardResult.Mapper

    @Binds
    abstract fun bindBaseDashboardItemMapper(
        dashboardItemMapper: BaseDashboardItemMapper): DashBoardItem.Mapper<FavoritePairUi>

    @Binds
    abstract fun bindDashboardItemsDataSource(
        dashBoardItemsDataSource: DashBoardItemsDataSource.Base): DashBoardItemsDataSource

    @Binds
    abstract fun bindDashboardRepository(
        dashboardRepository: BaseDashboardRepository): DashboardRepository

}