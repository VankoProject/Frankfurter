package com.kliachenko.presentation.main

import com.kliachenko.presentation.fake.FakeNavigation
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var navigation: FakeNavigation
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        navigation = FakeNavigation()
        viewModel = MainViewModel(navigation)
    }

    @Test
    fun firstRun() {
        viewModel.init(firstRun = true)
        navigation.checkNavigateToLoadScreen()
    }

    @Test
    fun notFirstRun() {
        viewModel.init(firstRun = false)
        navigation.checkNotCalled()
    }

}