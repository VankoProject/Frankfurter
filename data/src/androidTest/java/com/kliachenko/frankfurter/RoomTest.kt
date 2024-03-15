package com.kliachenko.frankfurter

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kliachenko.data.dashboard.cache.currencyCache.CurrencyDataBase
import com.kliachenko.data.dashboard.cache.currencyPair.CurrencyPairCache
import com.kliachenko.data.dashboard.cache.currencyPair.CurrencyPairDao
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomTest {

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
        dao = dataBase.currencyPairDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        dataBase.close()
    }

    @Test
    fun testInsert() = runBlocking {
        assertEquals(emptyList<CurrencyPairCache>(), dao.favoriteCurrencyPair())
        dao.insertCurrencyPair(CurrencyPairCache("A", "B"))
        var expected = listOf(CurrencyPairCache("A", "B"))
        assertEquals(expected, dao.favoriteCurrencyPair())

        dao.insertCurrencyPair(CurrencyPairCache("A", "B"))
        expected = listOf(CurrencyPairCache("A", "B"))
        assertEquals(expected, dao.favoriteCurrencyPair())

        dao.insertCurrencyPair(CurrencyPairCache("A", "C"))
        expected = listOf(CurrencyPairCache("A", "B"), CurrencyPairCache("A", "C"))
        assertEquals(expected, dao.favoriteCurrencyPair())
    }

    @Test
    fun tstRemove() = runBlocking {
        dao.insertCurrencyPair(CurrencyPairCache("A", "B", 1.0))
        assertEquals(listOf(CurrencyPairCache("A", "B", 1.0)), dao.favoriteCurrencyPair())

        dao.removeCurrencyPair(CurrencyPairCache("A", "B"))
        assertEquals(emptyList<CurrencyPairCache>(), dao.favoriteCurrencyPair())
    }

}