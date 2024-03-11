package com.kliachenko.presentation.load

import com.kliachenko.presentation.fake.FakeClear
import com.kliachenko.presentation.fake.FakeNavigation
import com.kliachenko.presentation.fake.FakeUiObservable
import com.kliachenko.presentation.loading.BaseLoadResultMapper
import com.kliachenko.presentation.loading.LoadViewModel
import org.junit.Before
import org.junit.Test

class BaseLoadResultMapperTest {

    private lateinit var observable: FakeUiObservable
    private lateinit var navigation: FakeNavigation
    private lateinit var clear: FakeClear
    private lateinit var loadResultMapper: BaseLoadResultMapper

    @Before
    fun setup() {
        observable = FakeUiObservable()
        navigation = FakeNavigation()
        clear = FakeClear()
        loadResultMapper = BaseLoadResultMapper(
            observable = observable,
            navigation = navigation,
            clearViewModel = clear
        )
    }

    @Test
    fun mapSuccess() {
        loadResultMapper.mapSuccess()
        navigation.checkNavigateToDashBoardScreen()
        clear.checkCalled(LoadViewModel::class.java)
    }

    @Test
    fun mapError() {
        loadResultMapper.mapError("No internet connection")
        observable.checkError()
    }
}