package com.kliachenko.frankfurter

import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.kliachenko.frankfurter.matchers.DrawableMatcher
import com.kliachenko.frankfurter.matchers.RecyclerViewMatcher
import com.kliachenko.presentation.R
import com.kliachenko.presentation.core.views.CustomButton
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not

class SettingPage {

    private val rootLayout =
        onView(
            allOf(
                withId(R.id.rootLayout),
                isAssignableFrom(ConstraintLayout::class.java)
            )
        )

    fun checkVisible() {
        rootLayout.check(matches(isDisplayed()))
    }

    fun checkNotVisible() {
        rootLayout.check(doesNotExist())
    }

    fun checkCurrenciesFrom(vararg currenciesFrom: String) {
        currenciesFrom.forEachIndexed { index, currency ->
            onView(
                RecyclerViewMatcher(
                    position = index,
                    targetViewId = R.id.currencyTextView,
                    recyclerViewId = R.id.recycleViewFrom
                )
            ).check(matches(withText(currency)))
        }
    }

    fun checkCurrenciesTo(vararg currenciesTo: String) {
        currenciesTo.forEachIndexed { index, currency ->
            onView(
                RecyclerViewMatcher(
                    position = index,
                    targetViewId = R.id.currencyTextView,
                    recyclerViewId = R.id.recycleViewTo
                )
            ).check(matches(withText(currency)))
        }
    }

    fun clickChoiceFrom(position: Int) {
        onView(
            RecyclerViewMatcher(
                position, R.id.currencyTextView, R.id.recycleViewFrom
            )
        ).perform(click())
    }

    fun clickChoiceTo(position: Int) {
        onView(
            RecyclerViewMatcher(
                position, R.id.currencyTextView, R.id.recycleViewTo
            )
        ).perform(click())
    }

    fun checkSelectedFrom(position: Int) {
        onView(
            RecyclerViewMatcher(
                position, R.id.selectedIconImageView, R.id.recycleViewFrom
            )
        ).check(matches(isDisplayed()))
            .check(matches(DrawableMatcher(R.drawable.selected_icon_48px)))
    }

    fun checkSelectedTo(position: Int) {
        onView(
            RecyclerViewMatcher(
                position, R.id.selectedIconImageView, R.id.recycleViewTo
            )
        ).check(matches(not(DrawableMatcher(R.drawable.selected_icon_48px))))
    }

    fun checkNotSelectedFrom(position: Int) {
        onView(
            RecyclerViewMatcher(
                position, R.id.selectedIconImageView, R.id.recycleViewFrom
            )
        ).check(matches(not(DrawableMatcher(R.drawable.selected_icon_48px))))
    }

    fun checkNotSelectedTo(position: Int) {
        onView(
            RecyclerViewMatcher(
                position, R.id.selectedIconImageView, R.id.recycleViewTo
            )
        ).check(matches(not(DrawableMatcher(R.drawable.selected_icon_48px))))
    }

    fun checkNoCurrencyTo() {
        onView(
            RecyclerViewMatcher(
                position = 0,
                targetViewId = R.id.stubSettingsTextView,
                recyclerViewId = R.id.recycleViewTo
            )
        ).check(matches(withText("NO MORE CURRENCIES")))
    }

    fun clickSave() {
        onView(
            allOf(
                withId(R.id.saveButton),
                withText("Save"),
                isAssignableFrom(CustomButton::class.java),
                withParent(withId(R.id.settingRootLayout)),
                withParent(isAssignableFrom(ConstraintLayout::class.java))
            )
        ).perform(click())
    }

    fun clickToDashBoard() {
        onView(
            allOf(
                withId(R.id.backButton),
                isAssignableFrom(ImageButton::class.java),
                withParent(withId(R.id.settingRootLayout)),
                withParent(isAssignableFrom(ConstraintLayout::class.java))
            )
        ).perform(click())
    }

}