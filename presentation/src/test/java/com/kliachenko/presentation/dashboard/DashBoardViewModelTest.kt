package com.kliachenko.presentation.dashboard

import com.kliachenko.domain.dashboard.DashBoardItem
import com.kliachenko.domain.dashboard.DashboardRepository
import com.kliachenko.domain.dashboard.DashboardResult
import com.kliachenko.presentation.core.Delimiter
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.fake.FakeNavigation
import com.kliachenko.presentation.fake.FakeRunAsync
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DashBoardViewModelTest {

    private lateinit var observable: FakeDashboardUiObservable
    private lateinit var navigation: FakeNavigation
    private lateinit var repository: FakeDashboardRepository
    private lateinit var runAsync: FakeRunAsync
    private lateinit var viewModel: DashBoardViewModel
    private lateinit var delimiter: Delimiter
    private lateinit var mapper: FakeDashboardResultMapper

    @Before
    fun setup() {
        observable = FakeDashboardUiObservable()
        navigation = FakeNavigation()
        repository = FakeDashboardRepository()
        runAsync = FakeRunAsync()
        delimiter = Delimiter.Base()
        mapper = FakeDashboardResultMapper()

        viewModel = DashBoardViewModel(
            observable = observable,
            navigation = navigation,
            repository = repository,
            runAsync = runAsync,
            delimiter = delimiter,
            mapper = mapper
        )
    }

    @Test
    fun empty() {
        repository.empty()
        viewModel.load()
        observable.checkProgress()
        runAsync.returnLoadResult()
        mapper.check(DashboardResult.Empty)
    }

    @Test
    fun errorThenSuccess() {
        repository.error()
        viewModel.load()
        observable.checkProgress()
        runAsync.returnLoadResult()
        mapper.check(DashboardResult.Error("Error"))

        repository.success()
        viewModel.retry()
        observable.checkProgress()
        runAsync.returnLoadResult()
        mapper.check(
            DashboardResult.Success(
                listOf(
                    DashBoardItem.Base(
                        "USD", "EUR", 1.0
                    ),
                    DashBoardItem.Base(
                        "USD", "JPY", 1.0
                    )
                )
            )
        )
    }

    @Test
    fun testDeleteFavoriteCurrencyPair() {
        repository.success()
        viewModel.retry()
        observable.checkProgress()
        runAsync.returnLoadResult()
        mapper.check(
            DashboardResult.Success(
                listOf(
                    DashBoardItem.Base(
                        "USD", "EUR", 1.0
                    ),
                    DashBoardItem.Base(
                        "USD", "JPY", 1.0
                    )
                )
            )
        )

        repository.empty()
        viewModel.remove("USD/EUR")
        repository.checkFromAndTo("USD", "EUR")
        observable.checkProgress()
        runAsync.returnLoadResult()
        mapper.check(DashboardResult.Empty)
    }

    @Test
    fun settings() {
        viewModel.openSettings()
        navigation.checkNavigateToSettings()
    }

    @Test
    fun lifecycle() {
        val observer: UpdateUi<DashboardUiState> = object : UpdateUi<DashboardUiState> {
            override fun updateUi(uiState: DashboardUiState) = Unit
        }
        viewModel.startGettingUpdates(observer = observer)
        observable.checkObserver(observer)

        viewModel.stopGettingUpdates()
        observable.checkEmptyObserver(observer)
    }

}

private class FakeDashboardResultMapper : DashboardResult.Mapper {

    private var actual: DashboardResult = DashboardResult.Empty

    override fun mapSuccess(items: List<DashBoardItem>) {
        actual = DashboardResult.Success(items = items)
    }

    override fun mapError(message: String) {
        actual = DashboardResult.Error(message)
    }

    override fun mapEmpty() {
        actual = DashboardResult.Empty
    }

    fun check(expected: DashboardResult) {
        Assert.assertEquals(expected, actual)
    }

}

private class FakeDashboardRepository : DashboardRepository {

    private var actualData: DashboardResult = DashboardResult.Empty

    private var actualFrom: String = ""

    private var actualTo: String = ""

    override suspend fun dashboardItems(): DashboardResult {
        return actualData
    }

    override suspend fun removeItem(from: String, to: String): DashboardResult {
        actualFrom = from
        actualTo = to
        return dashboardItems()
    }

    fun empty() {
        actualData = DashboardResult.Empty
    }

    fun error() {
        actualData = DashboardResult.Error("Error")
    }

    fun success() {
        actualData = DashboardResult.Success(
            listOf(
                DashBoardItem.Base(
                    "USD", "EUR", 1.0
                ),
                DashBoardItem.Base(
                    "USD", "JPY", 1.0
                )
            )
        )
    }

    fun checkFromAndTo(expectedFrom: String, expectedTo: String) {
        Assert.assertEquals(expectedFrom, actualFrom)
        Assert.assertEquals(expectedTo, actualTo)
    }

}


