package com.kliachenko.presentation.dashboard

import com.kliachenko.presentation.core.Delimiter
import com.kliachenko.presentation.dashboard.adapter.FavoritePairUi
import org.junit.Assert
import org.junit.Test

class BaseDashboardItemMapperTest {

    private val itemMapper = BaseDashboardItemMapper(Delimiter.Base("/"))

    @Test
    fun testSuccessMap() {
        val expected: FavoritePairUi = FavoritePairUi.Base(pair = "USD/EUR", rate = "2.0")
        val actual = itemMapper.mapItems("USD", "EUR", 2.0)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun testErrorMap() {
        val expected: FavoritePairUi = FavoritePairUi.Base(pair = "USD/ EUR", rate = "2")
        val actual = itemMapper.mapItems("USD", "EUR", 2.0)
        Assert.assertNotEquals(expected, actual)
    }
}