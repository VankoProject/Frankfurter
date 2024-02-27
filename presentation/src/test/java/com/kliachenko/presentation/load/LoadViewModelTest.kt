package com.kliachenko.presentation.load

import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.fake.FakeClear
import com.kliachenko.presentation.fake.FakeNavigation
import com.kliachenko.presentation.fake.FakeRunAsync
import com.kliachenko.presentation.fake.FakeUiObservable
import com.kliachenko.presentation.loading.BaseLoadResultMapper
import com.kliachenko.presentation.loading.LoadUiState
import com.kliachenko.presentation.loading.LoadViewModel
import org.junit.Before
import org.junit.Test


class LoadViewModelTest {

    private lateinit var viewModel: LoadViewModel
    private lateinit var runAsync: FakeRunAsync
    private lateinit var observable: FakeUiObservable
    private lateinit var repository: FakeCurrenciesRepository
    private lateinit var navigation: FakeNavigation
    private lateinit var clear: FakeClear

    @Before
    fun setup() {
        runAsync = FakeRunAsync()
        observable = FakeUiObservable()
        repository = FakeCurrenciesRepository()
        navigation = FakeNavigation()
        clear = FakeClear()
        viewModel = LoadViewModel(
            observable = observable,
            repository = repository,
            runAsync = runAsync,
            mapper = BaseLoadResultMapper(observable, navigation, clear)
        )
    }

    @Test
    fun testFirstRunErrorThenSuccess() {
        repository.expectError()
        viewModel.init(firstRun = true)
        observable.checkProgress()
        runAsync.returnLoadResult()
        observable.checkError()

        repository.expectSuccess()
        viewModel.load()

        observable.checkProgress()
        runAsync.returnLoadResult()
        navigation.checkNavigateToDashBoardScreen()
        clear.checkCalled(LoadViewModel::class.java)
    }

    @Test
    fun testFirstRunSuccess() {
        repository.expectSuccess()
        viewModel.init(firstRun = true)
        observable.checkProgress()
        runAsync.returnLoadResult()
        clear.checkCalled(LoadViewModel::class.java)
        navigation.checkNavigateToDashBoardScreen()
    }

    @Test
    fun lifeCycle() {
        val observer: UpdateUi<LoadUiState> = object : UpdateUi<LoadUiState> {
            override fun updateUi(uiState: LoadUiState) = Unit
        }
        viewModel.startGettingUpdates(observer = observer)
        observable.checkObserver(observer)

        viewModel.stopGettingUpdates()
        observable.checkEmpty(observer)
    }
}