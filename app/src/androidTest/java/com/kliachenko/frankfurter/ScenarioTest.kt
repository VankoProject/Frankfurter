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

        val dashboardPage = DashboardPage()
        loadPage.checkNotVisible()
        dashboardPage.checkVisible()
        dashboardPage.clickSettings()

        val settingPage = SettingPage()
        settingPage.checkVisible()
        dashboardPage.checkNotVisible()

        //выбор первой пары
        settingPage.checkCurrenciesFrom("USD", "EUR", "JPY")
        settingPage.clickFirstChoice(position = 0)
        settingPage.checkFirstChoice(position = 0)
        settingPage.checkNotSelected(position = 1)

        settingPage.checkCurrenciesTo("EUR", "JPY")
        settingPage.clickSecondChoice(position = 0)
        settingPage.checkSecondChoice(position = 0)
        settingPage.checkNotSelected(position = 1)
        settingPage.clickSave()

        //отображение ошибки, потом отображение пары с загруженным рейтингом
        dashboardPage.checkVisible()
        settingPage.checkNotVisible()
        dashboardPage.checkError(message = "Service unavailable")
        dashboardPage.clickRetry()
        dashboardPage.checkFavoritePair(currencyPair = "USD/EUR", rate = "1.0", position = 0)

        //возврат в настройки, выбор второй пары, сохранение
        dashboardPage.clickSettings()
        settingPage.checkVisible()
        dashboardPage.checkNotVisible()
        settingPage.checkCurrenciesFrom("USD", "EUR", "JPY")
        settingPage.clickFirstChoice(position = 0)
        settingPage.checkFirstChoice(position = 0)
        settingPage.checkCurrenciesTo("JPY")
        settingPage.clickSecondChoice(position = 0)
        settingPage.checkSecondChoice(position = 0)
        settingPage.clickSave()

        //отображение двух выбранных пар
        dashboardPage.checkVisible()
        settingPage.checkNotVisible()
        dashboardPage.checkFavoritePair(currencyPair = "USD/EUR", rate = "1.0", position = 0)
        dashboardPage.checkFavoritePair(currencyPair = "USD/JPY", rate = "1.0", position = 1)

        //возврат в настройки, выбор третьей пары для первого элемента - проверка на пустоту
        dashboardPage.clickSettings()
        settingPage.checkVisible()
        dashboardPage.checkNotVisible()
        settingPage.checkCurrenciesFrom("USD", "EUR", "JPY")
        settingPage.clickFirstChoice(position = 0)
        settingPage.checkFirstChoice(position = 0)
        settingPage.checkNoCurrencyTo("NO MORE CURRENCIES")

        //выбор другой пары и сразу навигация по верхней кнопке назад
        settingPage.clickFirstChoice(position = 1)
        settingPage.checkFirstChoice(position = 1)
        settingPage.checkCurrenciesTo("USD", "JPY")
        settingPage.clickToDashBoard()

        //удаление сохраненной пары
        dashboardPage.checkVisible()
        settingPage.checkNotVisible()
        dashboardPage.checkFavoritePair(currencyPair = "USD/EUR", rate = "1.0", position = 0)
        dashboardPage.checkFavoritePair(currencyPair = "USD/JPY", rate = "1.0", position = 1)
        dashboardPage.clickForRemove(currencyPair = "USD/EUR", rate = "1.0", position = 0)
        dashboardPage.clickConfirmForRemove(
            question = "Remove this pair from favorite list?",
            action = "Confirm"
        )
        //проверка, что вторая пара переместилась на 0-ую позицию
        dashboardPage.checkFavoritePair(currencyPair = "USD/JPY", rate = "1.0", position = 0)

        //проверка системной кнопки назад
        dashboardPage.clickSettings()
        settingPage.checkVisible()
        dashboardPage.checkNotVisible()
        Espresso.pressBack()
        dashboardPage.checkVisible()
        settingPage.checkNotVisible()
        dashboardPage.checkFavoritePair(currencyPair = "USD/JPY", rate = "1.0", position = 0)

    }
}