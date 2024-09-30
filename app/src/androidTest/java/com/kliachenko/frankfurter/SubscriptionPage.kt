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
import com.kliachenko.presentation.R
import com.kliachenko.presentation.core.views.CustomButton
import org.hamcrest.Matchers.allOf

class SubscriptionPage {

    private val rootLayout =
        onView(
            allOf(
                withId(R.id.subscriptionRootLayout),
                isAssignableFrom(LinearLayout::class.java)
            )
        )

    fun checkVisible() {
        rootLayout.check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.subscriptionTextView),
                withText("Buy premium subscription"),
                withParent(withId(R.id.subscriptionRootLayout))
            )
        ).check(matches(isDisplayed()))
    }

    fun clickComeback() {
        onView(
            allOf(
                withId(R.id.subscriptionBackButton),
                isAssignableFrom(ImageButton::class.java),
                withParent(withId(R.id.subscriptionRootLayout))
            )
        ).check(matches(DrawableMatcher(R.drawable.back_icon_48px)))
            .perform(click())

    }

    fun checkNotVisible() {
        rootLayout.check(doesNotExist())
    }

    fun clickBuy() {
        onView(
            allOf(
                withId(R.id.savePremiumButton),
                withText("Buy"),
                isAssignableFrom(CustomButton::class.java),
                withParent(withId(R.id.subscriptionRootLayout))
            )
        ).check(matches(isDisplayed()))
            .check(matches(ColorMatcher("#FFFFFFFF")))
            .perform(click())
    }

}
