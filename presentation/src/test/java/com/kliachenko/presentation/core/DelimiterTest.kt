package com.kliachenko.presentation.core

import org.junit.Assert.assertEquals
import org.junit.Test

class DelimiterTest {

    @Test
    fun testSplit() {
        val delimiter = Delimiter.Base(value = "/")
        val actual: List<String> = delimiter.split("USD/EUR")
        val expected:List<String> = listOf("USD", "EUR")
        assertEquals(expected, actual)
    }

    @Test
    fun concatTest() {
        val delimiter = Delimiter.Base(value = "/")
        val actual = delimiter.concat(from = "USD", to = "EUR")
        val expected = "USD/EUR"
        assertEquals(expected, actual)
    }
}