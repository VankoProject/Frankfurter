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
        settingPage.clickFirstChoice(position = 0)
        settingPage.checkFirstChoice(position = 0)
        settingPage.checkNotSelected(position = 1)
        settingPage.checkNotSelected(position = 2)

        settingPage.checkCurrenciesTo("EUR", "JPY")
        settingPage.clickSecondChoice(position = 0)
        settingPage.checkSecondChoice(position = 0)
        settingPage.checkNotSelected(position = 1)
        settingPage.checkNotSelected(position = 2)
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
        settingPage.clickFirstChoice(position = 0)
        settingPage.checkFirstIsChosen(position = 0)
        settingPage.checkCurrenciesTo("JPY")
        settingPage.clickSecondChoice(position = 0)
        settingPage.checkSelected(position = 0)
        settingPage.clickSave()
        settingPage.checkNotVisible()

        dashboardPage.checkVisible()
        dashboardPage.checkFavoritePair(currencyPair = "USD/EUR", rate = "1.0", position = 0)
        dashboardPage.checkFavoritePair(currencyPair = "USD/JPY", rate = "1.0", position = 1)

        dashboardPage.clickSettings()
        settingPage.checkVisible()
        dashboardPage.checkNotVisible()
        settingPage.checkCurrenciesFrom("USD", "EUR", "JPY")
        settingPage.clickFirstChoice(position = 0)
        settingPage.checkFirstChoice(position = 0)
        settingPage.checkNoCurrencyTo()

        settingPage.clickFirstChoice(position = 1)
        settingPage.checkFirstChoice(position = 1)
        settingPage.checkCurrenciesTo("USD", "JPY")
        settingPage.clickToDashBoard()

        dashboardPage.checkVisible()
        settingPage.checkNotVisible()
        dashboardPage.checkFavoritePair(currencyPair = "USD/EUR", rate = "1.0", position = 0)
        dashboardPage.checkFavoritePair(currencyPair = "USD/JPY", rate = "1.0", position = 1)
        dashboardPage.clickForRemove(position = 0)
        dashboardPage.clickConfirmForRemove()

        dashboardPage.checkFavoritePair(currencyPair = "USD/JPY", rate = "1.0", position = 0)

        dashboardPage.clickForRemove(position = 0)
        dashboardPage.clickConfirmForRemove()
        dashboardPage.checkEmptyFavoriteList()

        dashboardPage.clickSettings()
        settingPage.checkVisible()
        dashboardPage.checkNotVisible()
        Espresso.pressBack()
        dashboardPage.checkVisible()
        settingPage.checkNotVisible()

    }
}