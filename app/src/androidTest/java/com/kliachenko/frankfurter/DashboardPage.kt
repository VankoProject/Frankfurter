package com.kliachenko.frankfurter

import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.kliachenko.frankfurter.matchers.ColorMatcher
import com.kliachenko.frankfurter.matchers.DrawableMatcher
import com.kliachenko.frankfurter.matchers.RecyclerViewMatcher
import com.kliachenko.presentation.R
import org.hamcrest.Matchers.allOf

class DashboardPage {

    private val rootLayout = onView(
        allOf(
            withId(R.id.dashboardRootLayout),
            isAssignableFrom(LinearLayout::class.java)
        )
    )

    fun checkVisible() {
        rootLayout.check(matches(isDisplayed()))
    }

    fun checkNotVisible() {
        rootLayout.check(doesNotExist())
    }

    fun clickSettings() {
        onView(
            allOf(
                withId(R.id.settingsButton),
                isAssignableFrom(ImageButton::class.java),
                withParent(withId(R.id.dashboardRootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).perform(click())
            .check(matches(DrawableMatcher(R.drawable.settings_icon_48px)))
    }

    fun clickRetry() {
        onView(
            RecyclerViewMatcher(position = 0, R.id.retryButton, R.id.dashboardRecycleView)
        ).check(matches(withText("Retry")))
            .perform(click())
    }

    fun clickForRemove(position: Int) {
        onView(RecyclerViewMatcher(position, R.id.favoritePairLayout, R.id.dashboardRecycleView))
            .perform(click())
    }

    fun clickConfirmForRemove() {
        onView(
            allOf(
                withId(com.google.android.material.R.id.snackbar_text),
                withText("Remove this pair from favorite list?")
            )
        ).check(matches(isDisplayed()))

        onView(
            withText("Confirm")
        ).perform(click())
    }

    fun checkEmptyFavoriteList() {
        onView(RecyclerViewMatcher(position = 0, R.id.stubTextView, R.id.dashboardRecycleView))
            .check(matches(withText("Go to settings and add new pair")))
    }

    fun checkError(message: String) {
        onView(
            RecyclerViewMatcher(position = 0, R.id.errorTextView, R.id.dashboardRecycleView)
        ).check(matches(withText(message)))
            .check(matches(ColorMatcher("#FF0000")))
    }

    fun checkFavoritePair(currencyPair: String, rate: String, position: Int) {
        onView(RecyclerViewMatcher(position, R.id.currencyPairTextView, R.id.dashboardRecycleView))
            .check(matches(withText(currencyPair)))
        onView(RecyclerViewMatcher(position, R.id.rateTextView, R.id.dashboardRecycleView))
            .check(matches(withText(rate)))
    }

}