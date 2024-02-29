package com.kliachenko.data.core

import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.UnknownHostException

class HandleErrorTest {

    @Test
    fun testUnknownHostException() {
        val provideResources: ProvideResources = FakeProvideResources()
        val handleError: HandleError = HandleError.Base(provideResources)
        val expected = "No internet connection"
        val actual: String = handleError.handle(UnknownHostException())
        assertEquals(expected, actual)
    }

    @Test
    fun testServiceUnavailable() {
        val provideResources: ProvideResources = FakeProvideResources()
        val handleError: HandleError = HandleError.Base(provideResources)
        val expected = "Service unavailable"
        val actual: String = handleError.handle(IllegalStateException())
        assertEquals(expected, actual)
    }
}

private class FakeProvideResources : ProvideResources {
    override fun noInternetConnectionMessage() = "No internet connection"

    override fun serviceUnavailableMessage() = "Service unavailable"
}