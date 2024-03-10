package com.kliachenko.presentation.subscritpion

import com.kliachenko.domain.settings.PremiumUserStorage
import com.kliachenko.presentation.fake.FakeClear
import com.kliachenko.presentation.fake.FakeNavigation
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SubscriptionViewModelTest {

    private lateinit var viewModel: SubscriptionViewModel
    private lateinit var premiumUserStorage: FakePremiumUserStorage
    private lateinit var navigation: FakeNavigation
    private lateinit var clear: FakeClear

    @Before
    fun setup() {
        clear = FakeClear()
        premiumUserStorage = FakePremiumUserStorage()
        navigation = FakeNavigation()
        viewModel = SubscriptionViewModel(
            premiumUserStorage = premiumUserStorage,
            clear = clear,
            navigation = navigation
        )
    }

    @Test
    fun testBuyPremium() {
        viewModel.buyPremium()
        premiumUserStorage.checkSavePremium()
        navigation.checkPopBackStack()
        clear.checkCalled(SubscriptionViewModel::class.java)
    }

    @Test
    fun testComeback() {
        viewModel.goSettingScreen()
        premiumUserStorage.checkNotSavePremium()
        navigation.checkPopBackStack()
        clear.checkCalled(SubscriptionViewModel::class.java)

    }

}

private class FakePremiumUserStorage : PremiumUserStorage.Save {

    private var actual: Boolean = false

    override fun savePremiumUser() {
        actual = true
    }

    fun checkSavePremium() {
        val expected = true
        Assert.assertEquals(expected, actual)
    }

    fun checkNotSavePremium() {
        val expected = false
        Assert.assertEquals(expected, actual)
    }

}
