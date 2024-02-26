package com.kliachenko.presentation.load

import com.kliachenko.domain.CurrencyModel
import com.kliachenko.domain.LoadResult
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.loading.LoadUiState
import com.kliachenko.presentation.loading.LoadViewModel
import org.junit.Before
import org.junit.Test


class LoadViewModelTest {

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

        viewModel.load()

        viewModel.retry()
        observable.checkProgress()
        runAsync.returnLoadResult()
        repository.checkLoadData(LoadResult.Success(listOf(CurrencyModel("A", "A"))))
        navigation.checkNavigateToDashBoardScreen()
        clear.checkCalled(LoadViewModel::class.java)
    }

    @Test
    fun testFirstRunSuccess() {
        repository.noCacheData()
        viewModel.init()
        observable.checkProgress()
        runAsync.returnLoadResult()
        repository.checkLoadData(LoadResult.Success(listOf(CurrencyModel("A", "A"))))
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
        val observer: UpdateUi<LoadUiState> = object : UpdateUi<LoadUiState> {
            override fun updateUi(uiState: LoadUiState) = Unit
        }
        viewModel.startGettingUpdates(observer = observer)
        observable.checkObserver(observer)

        viewModel.stopGettingUpdates()
        observable.checkEmpty(observer)
    }
}