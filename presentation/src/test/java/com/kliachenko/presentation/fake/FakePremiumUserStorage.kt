package com.kliachenko.presentation.fake

import com.kliachenko.domain.settings.PremiumUserStorage
import org.junit.Assert

class FakePremiumUserStorage : PremiumUserStorage.Mutable {

    private var actual: Boolean = false

    override fun savePremiumUser() {
        actual = true
    }

    override fun isPremium() = actual

    fun checkSavePremium() {
        val expected = true
        Assert.assertEquals(expected, actual)
    }

    fun checkNotSavePremium() {
        val expected = false
        Assert.assertEquals(expected, actual)
    }

}