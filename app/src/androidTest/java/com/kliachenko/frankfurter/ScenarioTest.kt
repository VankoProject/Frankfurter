package com.kliachenko.frankfurter

import androidx.test.espresso.Espresso
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kliachenko.presentation.main.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun happyPass() {
        val loadPage = LoadPage()
        loadPage.checkVisible()
        loadPage.checkError("No internet connection")
        loadPage.clickRetry()
        loadPage.checkNotVisible()

        val dashboardPage = DashboardPage()
        dashboardPage.checkVisible()
        dashboardPage.clickSettings()
        dashboardPage.checkNotVisible()

        val settingPage = SettingPage()
        settingPage.checkVisible()

        settingPage.checkCurrenciesFrom("USD", "EUR", "JPY")
        settingPage.clickChoiceFrom(position = 0)
        settingPage.checkSelectedFrom(position = 0)
        settingPage.checkNotSelectedFrom(position = 1)
        settingPage.checkNotSelectedFrom(position = 2)

        settingPage.checkCurrenciesTo("EUR", "JPY")
        settingPage.clickChoiceTo(position = 0)
        settingPage.checkSelectedTo(position = 0)
        settingPage.checkNotSelectedTo(position = 1)
        settingPage.clickSave()
        settingPage.checkNotVisible()

        dashboardPage.checkVisible()
        dashboardPage.checkError(message = "Service unavailable")
        dashboardPage.clickRetry()
        dashboardPage.checkFavoritePair(currencyPair = "USD/EUR", rate = "1.0", position = 0)

        dashboardPage.clickSettings()
        dashboardPage.checkNotVisible()
        settingPage.checkVisible()

        settingPage.checkCurrenciesFrom("USD", "EUR", "JPY")
        settingPage.clickChoiceFrom(position = 0)
        settingPage.checkSelectedFrom(position = 0)
        settingPage.checkCurrenciesTo("JPY") //todo add position
        settingPage.clickChoiceTo(position = 0)
        settingPage.checkSelectedTo(position = 0)
        settingPage.clickSave()
        settingPage.checkNotVisible()

        dashboardPage.checkVisible()
        dashboardPage.checkFavoritePair(currencyPair = "USD/EUR", rate = "1.0", position = 0)
        dashboardPage.checkFavoritePair(currencyPair = "USD/JPY", rate = "1.0", position = 1)

        dashboardPage.clickSettings()
        dashboardPage.checkNotVisible()
        settingPage.checkVisible()
        settingPage.checkCurrenciesFrom("USD", "EUR", "JPY")
        settingPage.clickChoiceFrom(position = 0)
        settingPage.checkSelectedFrom(position = 0)
        settingPage.checkNoCurrencyTo()

        settingPage.clickChoiceFrom(position = 1)
        settingPage.checkSelectedFrom(position = 1)
        settingPage.checkCurrenciesTo("USD", "JPY")
        settingPage.clickToDashBoard()
        settingPage.checkNotVisible()

        dashboardPage.checkVisible()
        dashboardPage.checkFavoritePair(currencyPair = "USD/EUR", rate = "1.0", position = 0)
        dashboardPage.checkFavoritePair(currencyPair = "USD/JPY", rate = "1.0", position = 1)
        dashboardPage.clickForRemove(position = 0)
        dashboardPage.clickConfirmForRemove()

        dashboardPage.checkFavoritePair(currencyPair = "USD/JPY", rate = "1.0", position = 0)

        dashboardPage.clickForRemove(position = 0)
        dashboardPage.clickConfirmForRemove()
        dashboardPage.checkEmptyFavoriteList()

        dashboardPage.clickSettings()
        dashboardPage.checkNotVisible()
        settingPage.checkVisible()
        Espresso.pressBack()
        settingPage.checkNotVisible()
        dashboardPage.checkVisible()

    }
}