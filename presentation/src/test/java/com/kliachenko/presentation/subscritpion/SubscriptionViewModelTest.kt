package com.kliachenko.presentation.subscritpion

import com.kliachenko.presentation.fake.FakeNavigation
import com.kliachenko.presentation.fake.FakePremiumUserStorage
import org.junit.Before
import org.junit.Test

class SubscriptionViewModelTest {

    private lateinit var viewModel: SubscriptionViewModel
    private lateinit var premiumUserStorage: FakePremiumUserStorage
    private lateinit var navigation: FakeNavigation

    @Before
    fun setup() {
        premiumUserStorage = FakePremiumUserStorage()
        navigation = FakeNavigation()
        viewModel = SubscriptionViewModel(
            premiumUserStorage = premiumUserStorage,
            navigation = navigation
        )
    }

    @Test
    fun testBuyPremium() {
        viewModel.buyPremium()
        premiumUserStorage.checkSavePremium()
        navigation.checkPopBackStack()
    }

    @Test
    fun testComeback() {
        viewModel.goSettingScreen()
        premiumUserStorage.checkNotSavePremium()
        navigation.checkPopBackStack()
    }

}


