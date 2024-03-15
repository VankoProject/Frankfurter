package com.kliachenko.data.cloud

import com.kliachenko.data.dashboard.cloud.currencyRate.CurrencyRateCloudDataSource
import com.kliachenko.data.dashboard.cloud.currencyRate.CurrencyRateService
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyRateServiceTest {

    private val baseUrl = "https://www.frankfurter.app/"
    private lateinit var retrofit: Retrofit
    private lateinit var apiService: CurrencyRateService

    @Before
    fun setup() {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(CurrencyRateService::class.java)
    }

    @Test
    fun testService() = runBlocking {
        val response = apiService.currencyValue("USD", "AUD").execute()
        Assert.assertTrue(response.isSuccessful)

    }

    @Test
    fun cloudDataSource() = runBlocking {
        val dataSource = CurrencyRateCloudDataSource.Base(apiService)
        val actual: Double = dataSource.rate("USD", "AUD")
        Assert.assertNotEquals(0.01, actual, 0.01)
    }
}

