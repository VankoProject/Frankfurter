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
    fun scenarioTest() {
        val loadPage = LoadPage()
        loadPage.checkVisible()
        loadPage.checkError("No internet connection")
        activityScenarioRule.scenario.recreate()
        loadPage.checkError("No internet connection")
        loadPage.clickRetry()
        loadPage.checkNotVisible()

        val dashboardPage = DashboardPage()
        dashboardPage.checkVisible()
        dashboardPage.checkEmptyFavoriteList()
        activityScenarioRule.scenario.recreate()
        dashboardPage.checkEmptyFavoriteList()
        dashboardPage.clickSettings()
        dashboardPage.checkNotVisible()

        val settingPage = SettingPage()
        settingPage.checkVisible()

        settingPage.checkCurrenciesFrom("EUR", "JPY", "USD")
        activityScenarioRule.scenario.recreate()
        settingPage.checkCurrenciesFrom("EUR", "JPY", "USD")
        settingPage.clickChoiceFrom(position = 0)
        settingPage.checkSelectedFrom(position = 0)
        activityScenarioRule.scenario.recreate()
        settingPage.checkSelectedFrom(position = 0)
        settingPage.checkNotSelectedFrom(position = 1)
        settingPage.checkNotSelectedFrom(position = 2)

        settingPage.checkCurrenciesTo("JPY", "USD")
        activityScenarioRule.scenario.recreate()
        settingPage.checkCurrenciesTo("JPY", "USD")
        settingPage.clickChoiceTo(position = 0)
        settingPage.checkSelectedTo(position = 0)
        activityScenarioRule.scenario.recreate()
        settingPage.checkSelectedTo(position = 0)
        settingPage.checkNotSelectedTo(position = 1)
        settingPage.clickSave()
        settingPage.checkNotVisible()

        dashboardPage.checkVisible()
        dashboardPage.checkError(message = "Service unavailable")
        dashboardPage.clickRetry()
        dashboardPage.checkFavoritePair(currencyPair = "EUR/JPY", rate = "15.5", position = 0)
        activityScenarioRule.scenario.recreate()
        dashboardPage.checkFavoritePair(currencyPair = "EUR/JPY", rate = "15.5", position = 0)

        dashboardPage.clickSettings()
        dashboardPage.checkNotVisible()
        settingPage.checkVisible()

        settingPage.checkCurrenciesFrom("EUR", "JPY", "USD")
        settingPage.clickChoiceFrom(position = 0)
        settingPage.checkSelectedFrom(position = 0)
        activityScenarioRule.scenario.recreate()
        settingPage.clickChoiceFrom(position = 0)
        settingPage.checkSelectedFrom(position = 0)
        settingPage.checkCurrenciesFrom("EUR", "JPY", "USD")
        settingPage.checkCurrenciesTo("USD")
        activityScenarioRule.scenario.recreate()
        settingPage.checkCurrenciesFrom("EUR", "JPY", "USD")
        settingPage.checkCurrenciesTo("USD")
        settingPage.clickChoiceTo(position = 0)
        settingPage.checkSelectedTo(position = 0)
        activityScenarioRule.scenario.recreate()
        settingPage.checkSelectedTo(position = 0)
        settingPage.clickSave()
        settingPage.checkNotVisible()

        dashboardPage.checkVisible()
        dashboardPage.checkError(message = "Service unavailable")
        dashboardPage.clickRetry()
        dashboardPage.checkFavoritePair(currencyPair = "EUR/JPY", rate = "15.5", position = 0)
        dashboardPage.checkFavoritePair(currencyPair = "EUR/USD", rate = "15.5", position = 1)
        activityScenarioRule.scenario.recreate()
        dashboardPage.checkFavoritePair(currencyPair = "EUR/JPY", rate = "15.5", position = 0)
        dashboardPage.checkFavoritePair(currencyPair = "EUR/USD", rate = "15.5", position = 1)

        dashboardPage.clickSettings()
        dashboardPage.checkNotVisible()
        settingPage.checkVisible()
        settingPage.checkCurrenciesFrom("EUR", "JPY", "USD")
        activityScenarioRule.scenario.recreate()
        settingPage.checkCurrenciesFrom("EUR", "JPY", "USD")
        settingPage.clickChoiceFrom(position = 0)
        settingPage.checkSelectedFrom(position = 0)
        activityScenarioRule.scenario.recreate()
        settingPage.checkSelectedFrom(position = 0)
        settingPage.checkNoCurrencyTo()

        settingPage.clickChoiceFrom(position = 1)
        settingPage.checkSelectedFrom(position = 1)
        activityScenarioRule.scenario.recreate()
        settingPage.checkSelectedFrom(position = 1)
        settingPage.checkCurrenciesTo("EUR", "USD")
        activityScenarioRule.scenario.recreate()
        settingPage.checkCurrenciesTo("EUR", "USD")
        settingPage.clickToDashBoard()
        settingPage.checkNotVisible()

        dashboardPage.checkVisible()

        dashboardPage.checkFavoritePair(currencyPair = "EUR/JPY", rate = "15.5", position = 0)
        dashboardPage.checkFavoritePair(currencyPair = "EUR/USD", rate = "15.5", position = 1)
        activityScenarioRule.scenario.recreate()
        dashboardPage.checkFavoritePair(currencyPair = "EUR/JPY", rate = "15.5", position = 0)
        dashboardPage.checkFavoritePair(currencyPair = "EUR/USD", rate = "15.5", position = 1)
        dashboardPage.clickForRemove(position = 0)
        dashboardPage.clickConfirmForRemove()

        dashboardPage.checkFavoritePair(currencyPair = "EUR/USD", rate = "15.5", position = 0)
        activityScenarioRule.scenario.recreate()
        dashboardPage.checkFavoritePair(currencyPair = "EUR/USD", rate = "15.5", position = 0)

        dashboardPage.clickForRemove(position = 0)
        dashboardPage.clickConfirmForRemove()
        dashboardPage.checkEmptyFavoriteList()
        activityScenarioRule.scenario.recreate()
        dashboardPage.checkEmptyFavoriteList()

        dashboardPage.clickSettings()
        dashboardPage.checkNotVisible()
        settingPage.checkVisible()
        Espresso.pressBack()
        settingPage.checkNotVisible()
        dashboardPage.checkVisible()

    }
}