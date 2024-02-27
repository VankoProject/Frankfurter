package com.kliachenko.data

import com.kliachenko.data.loading.BaseLoadCurrencyRepository
import com.kliachenko.data.loading.cache.CurrencyCache
import com.kliachenko.data.loading.cache.CurrencyCacheDataSource
import com.kliachenko.data.loading.cloud.LoadCurrencyCloudDataSource
import com.kliachenko.domain.load.LoadCurrenciesResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class BaseLoadCurrencyRepositoryTest {

    private lateinit var cloudDataSource: FakeCloudDataSource
    private lateinit var cacheDataSource: FakeCurrencyCacheDataSource
    private lateinit var repository: BaseLoadCurrencyRepository
    private lateinit var provideResources: FakeProvideResources

    @Before
    fun setup() {
        cacheDataSource = FakeCurrencyCacheDataSource()
        cloudDataSource = FakeCloudDataSource()
        provideResources = FakeProvideResources()
        repository = BaseLoadCurrencyRepository(
            loadCurrencyCloudDataSource = cloudDataSource,
            currencyCacheDataSource = cacheDataSource,
            provideResources = provideResources
        )
    }

    @Test
    fun successLoadCurrenciesCacheEmpty() = runBlocking {
        cacheDataSource.cacheEmpty()
        cloudDataSource.successLoadResult()
        val actual = repository.loadCurrencies()
        val expected = LoadCurrenciesResult.Success
        Assert.assertEquals(expected, actual)
    }
    @Test
    fun successLoadCurrenciesCacheNotEmpty() = runBlocking {
        cacheDataSource.cacheNotEmpty()
        cloudDataSource.successLoadResult()
        val actual = repository.loadCurrencies()
        val expected = LoadCurrenciesResult.Success
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun noConnectionError() = runBlocking {
        cacheDataSource.cacheEmpty()
        cloudDataSource.errorLoadResult(exception = UnknownHostException())
        val actual = repository.loadCurrencies()
        val expected = LoadCurrenciesResult.Error(message = "No internet connection")
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun serviceUnavailableError() = runBlocking {
        cacheDataSource.cacheEmpty()
        cloudDataSource.errorLoadResult(exception = IllegalStateException())
        val actual = repository.loadCurrencies()
        val expected = LoadCurrenciesResult.Error(message = "Service unavailable")
        Assert.assertEquals(expected, actual)
    }
}

private class FakeCloudDataSource : LoadCurrencyCloudDataSource {

    private var isSuccessResult: Boolean = false
    private lateinit var exception: Exception

    override suspend fun loadCurrencies(): HashMap<String, String> {
        if (isSuccessResult) {
            return hashMapOf("A" to "A", "B" to "B")
        } else throw exception
    }

    fun successLoadResult() {
        isSuccessResult = true
    }

    fun errorLoadResult(exception: Exception) {
        this.exception = exception
    }

}

private class FakeProvideResources : ProvideResources {

    override fun noInternetConnectionMessage() = "No internet connection"

    override fun serviceUnavailableMessage() = "Service unavailable"

}

private class FakeCurrencyCacheDataSource : CurrencyCacheDataSource.Mutable {

    private var actualCurrencies: List<CurrencyCache> = emptyList()

    override suspend fun save(currencies: List<CurrencyCache>) {
        actualCurrencies = currencies
    }

    override suspend fun read(): List<CurrencyCache> {
        return actualCurrencies
    }

    fun cacheEmpty() {
        actualCurrencies = emptyList()
    }

    fun cacheNotEmpty() {
        actualCurrencies = listOf(CurrencyCache("A", "A"))
    }

}