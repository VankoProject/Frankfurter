package com.kliachenko.data.loading

import com.kliachenko.data.ProvideResources
import com.kliachenko.data.loading.cache.CurrencyCache
import com.kliachenko.data.loading.cache.CurrencyCacheDataSource
import com.kliachenko.data.loading.cloud.LoadCurrencyCloudDataSource
import com.kliachenko.domain.load.LoadCurrenciesRepository
import com.kliachenko.domain.load.LoadCurrenciesResult
import java.net.UnknownHostException

class BaseLoadCurrencyRepository(
    private val loadCurrencyCloudDataSource: LoadCurrencyCloudDataSource,
    private val currencyCacheDataSource: CurrencyCacheDataSource.Mutable,
    private val provideResources: ProvideResources,
) : LoadCurrenciesRepository {

    override suspend fun loadCurrencies(): LoadCurrenciesResult = try {
        if (currencyCacheDataSource.read().isEmpty()) {
            val result = loadCurrencyCloudDataSource.loadCurrencies().map {
                CurrencyCache(it.key, it.value)
            }
            currencyCacheDataSource.save(result)
        }
        LoadCurrenciesResult.Success
    } catch (e: Exception) {
        LoadCurrenciesResult.Error(
            if (e is UnknownHostException)
                provideResources.noInternetConnectionMessage()
            else
                provideResources.serviceUnavailableMessage()
        )
    }
}