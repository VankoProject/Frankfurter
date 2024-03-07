package com.kliachenko.frankfurter

import android.widget.FrameLayout
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
import com.kliachenko.frankfurter.matchers.ColorMatcher
import com.kliachenko.presentation.R
import com.kliachenko.presentation.core.views.CustomButton
import com.kliachenko.presentation.core.views.CustomTextView
import org.hamcrest.Matchers.allOf

class LoadPage {

    private val rootLayout =
        onView(
            allOf(
                withId(R.id.rootLayout),
                isAssignableFrom(ConstraintLayout::class.java),
                withParent(withId(R.id.container)),
                withParent(isAssignableFrom(FrameLayout::class.java))
            )
        )

    fun checkVisible() {
        rootLayout.check(matches(isDisplayed()))
    }

    fun checkNotVisible() {
        rootLayout.check(doesNotExist())
    }

    fun checkError(error: String) {
        onView(
            allOf(
                withId(R.id.errorTextView),
                withText(error),
                isAssignableFrom(CustomTextView::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(ConstraintLayout::class.java))
            )
        ).check(matches(isDisplayed()))
            .check(matches(ColorMatcher("#FF0000")))
    }

    fun clickRetry() {
        onView(
            allOf(
                withId(R.id.retryButton),
                isAssignableFrom(CustomButton::class.java),
                withText("Retry"),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(ConstraintLayout::class.java))
            )
        ).check(matches(isDisplayed()))
            .perform(click())
    }

}