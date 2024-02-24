package com.kliachenko.presentation.load

import org.junit.Before
import org.junit.Test


class MainViewModelTest {

    private lateinit var viewModel: LoadViewModel
    private lateinit var runAsync: FakeRunAsync
    private lateinit var observable: FakeUiObservable
    private lateinit var repository: FakeRepository
    private lateinit var navigation: FakeNavigation
    private lateinit var clear: FakeClear

    @Before
    fun setup() {
        runAsync = FakeRunAsync()
        observable = FakeUiObservable()
        repository = FakeRepository()
        navigation = FakeNavigation()
        clear = FakeClear()
        viewModel = LoadViewModel(
            observable = observable,
            repository = repository,
            navigation = navigation,
            clear = clear,
            runAsync = runAsync
        )
    }

    @Test
    fun testFirstRunError() {
        repository.noCacheData()
        viewModel.init()
        observable.checkProgress()
        runAsync.returnLoadResult()
        observable.checkError()

        viewModel.retry()
        observable.checkProgress()
        runAsync.returnLoadResult()
        repository.checkLoadData(listOf("A", "B", "C"))
        navigation.checkNavigateToDashBoardScreen()
        clear.checkCalled(LoadViewModel::class.java)
    }

    @Test
    fun testFirstRunSuccess() {
        repository.noCacheData()
        viewModel.init()
        observable.checkProgress()
        runAsync.returnLoadResult()
        repository.checkLoadData(listOf("A", "B", "C"))
        navigation.checkNavigateToDashBoardScreen()
        clear.checkCalled(LoadViewModel::class.java)
    }

    @Test
    fun testHasLoadData() {
        repository.hasCacheData()
        viewModel.init()
        observable.checkProgress()
        navigation.checkNavigateToDashBoardScreen()
        clear.checkCalled(LoadViewModel::class.java)
    }

    @Test
    fun lifeCycle() {
        val observer: UpdateUI<LoadingUiState> = object : UpdateUi<LoadingUiState> {
            override fun updateUi(uiState: LoadingUiState) = Unit
        }
        viewModel.startGettingUpdates(observer = observer)
        observable.checkObserver(observer)

        viewModel.stopGettingUpdates()
        observer.checkEmpty(observer)
    }
}