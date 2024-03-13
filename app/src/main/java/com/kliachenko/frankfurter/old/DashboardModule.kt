//package com.kliachenko.frankfurter.old
//
//import com.kliachenko.data.core.HandleError
//import com.kliachenko.data.dashboard.BaseDashboardRepository
//import com.kliachenko.data.dashboard.DashBoardItemsDataSource
//import com.kliachenko.data.dashboard.UpdatedRate
//import com.kliachenko.data.dashboard.cache.CurrentTimeInMillis
//import com.kliachenko.data.dashboard.cache.FavoritePairCacheDataSource
//import com.kliachenko.frankfurter.core.Core
//import com.kliachenko.frankfurter.core.ProvideInstance
//import com.kliachenko.presentation.dashboard.BaseDashboardItemMapper
//import com.kliachenko.presentation.dashboard.BaseDashboardResultMapper
//import com.kliachenko.presentation.dashboard.DashBoardViewModel
//import com.kliachenko.presentation.dashboard.DashboardUiObservable
//
//class DashboardModule(
//    private val core: Core,
//    private val provideInstance: ProvideInstance,
//    private val clear: Clear,
//) : Module<DashBoardViewModel> {
//
//    override fun viewModel(): DashBoardViewModel {
//        val observable = DashboardUiObservable.Base()
//        val cacheDataSource = FavoritePairCacheDataSource.Base(
//            core.provideCurrencyDataBase().dataBase().currencyPairDao()
//        )
//        val currentTimeInMillis = CurrentTimeInMillis.Base()
//        return DashBoardViewModel(
//            observable = observable,
//            navigation = core.provideNavigation(),
//            repository = BaseDashboardRepository(
//                favoriteCacheDataSource = cacheDataSource,
//                handleError = HandleError.Base(core.provideResources()),
//                dashBoardItemsDataSource = DashBoardItemsDataSource.Base(
//                    currentTimeInMillis = currentTimeInMillis,
//                    updatedRate = UpdatedRate.Base(
//                        cacheDataSource,
//                        currentTimeInMillis,
//                        provideInstance.provideLoadRateCloudDataSource(
//                            core.provideRetrofit()
//                        )
//                    ),
//                )
//            ),
//            runAsync = core.provideRunAsync(),
//            clear = clear,
//            delimiter = core.provideDelimiter(),
//            mapper = BaseDashboardResultMapper(
//                observable,
//                itemMapper = BaseDashboardItemMapper(core.provideDelimiter())
//            )
//        )
//    }
//}