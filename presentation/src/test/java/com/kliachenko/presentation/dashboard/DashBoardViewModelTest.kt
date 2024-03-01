package com.kliachenko.presentation.dashboard

import com.kliachenko.domain.dashboard.DashBoardItem
import com.kliachenko.domain.dashboard.DashboardRepository
import com.kliachenko.domain.dashboard.DashboardResult
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.fake.FakeClear
import com.kliachenko.presentation.fake.FakeNavigation
import com.kliachenko.presentation.fake.FakeRunAsync
import org.junit.Before
import org.junit.Test

class DashBoardViewModelTest {

    private lateinit var observable: FakeDashboardUiObservable
    private lateinit var navigation: FakeNavigation
    private lateinit var repository: FakeDashboardRepository
    private lateinit var clear: FakeClear
    private lateinit var runAsync: FakeRunAsync
    private lateinit var viewModel: DashBoardViewModel

    @Before
    fun setup() {
        observable = FakeDashboardUiObservable()
        navigation = FakeNavigation()
        repository = FakeDashboardRepository()
        clear = FakeClear()
        runAsync = FakeRunAsync()
        viewModel = DashBoardViewModel(
            observable = observable,
            navigation = navigation,
            repository = repository,
            runAsync = runAsync,
            clear = clear
        )
    }

    @Test
    fun empty() {
        repository.empty()
        viewModel.load()
        observable.checkProgress()
        runAsync.returnLoadResult()
        observable.checkEmpty()
    }

    @Test
    fun errorThenSuccess() {
        repository.error()
        viewModel.load()
        observable.checkProgress()
        runAsync.returnLoadResult()
        observable.checkError()

        repository.success()
        viewModel.retry()
        observable.checkProgress()
        runAsync.returnLoadResult()
        observable.checkSuccess()
    }

    @Test
    fun settings() {
        viewModel.openSettings()
        navigation.checkNavigateToSettings()
        clear.checkCalled(DashBoardViewModel::class.java)
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

private class FakeDashboardRepository : DashboardRepository {

    private var actualData: DashboardResult = DashboardResult.Empty

    override suspend fun dashboardItems(): DashboardResult {
        return actualData
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
                )
            )
        )
    }


}


