package com.kliachenko.frankfurter

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kliachenko.data.dashboard.cache.CurrencyPair
import com.kliachenko.data.dashboard.cache.CurrencyPairDao
import com.kliachenko.data.loading.cache.CurrencyDataBase
import com.kliachenko.data.loading.cache.ProvideCurrencyDataBase
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var provideDataBase: ProvideCurrencyDataBase
    private lateinit var dataBase: CurrencyDataBase
    private lateinit var dao: CurrencyPairDao

    @Before
    fun setup() {
        val context: Context = ApplicationProvider.getApplicationContext()
        dataBase = Room.inMemoryDatabaseBuilder(
            context,
            CurrencyDataBase::class.java
        )
            .allowMainThreadQueries()
            .build()
        provideDataBase = ProvideCurrencyDataBase.Base(context)
        dao = provideDataBase.dataBase().currencyPairDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        dataBase.close()
    }

    @Test
    fun test() = runBlocking {
        assertEquals(emptyList<CurrencyPair>(), dao.favoriteCurrencyPair())
        dao.insertCurrencyPair(CurrencyPair("A", "B"))
        val expected = listOf(CurrencyPair("A", "B"))
        assertEquals(expected, dao.favoriteCurrencyPair())
    }
}