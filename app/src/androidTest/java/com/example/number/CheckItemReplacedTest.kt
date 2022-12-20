package com.example.number

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.number.main.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CheckItemReplacedTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_not_duplicated_item() {
        onView(ViewMatchers.withId(R.id.editText)).perform(typeText("10"))
        closeSoftKeyboard()

        onView(ViewMatchers.withId(R.id.getFactButton)).perform(click())
        onView(RecycleViewMatcher(R.id.historyRecycleView).atPosition(0, R.id.titleTextView))
            .check(matches(withText("10")))
        onView(
            RecycleViewMatcher(R.id.historyRecycleView)
                .atPosition(0, R.id.subTitleTextView)
        )
            .check(matches(withText("fact about 10")))

        onView(ViewMatchers.withId(R.id.editText)).perform(typeText("11"))
        closeSoftKeyboard()

        onView(ViewMatchers.withId(R.id.getFactButton)).perform(click())
        onView(RecycleViewMatcher(R.id.historyRecycleView).atPosition(0, R.id.titleTextView))
            .check(matches(withText("11")))
        onView(
            RecycleViewMatcher(R.id.historyRecycleView)
                .atPosition(0, R.id.subTitleTextView)
        )
            .check(matches(withText("fact about 11")))

        onView(RecycleViewMatcher(R.id.historyRecycleView).atPosition(1, R.id.titleTextView))
            .check(matches(withText("10")))
        onView(
            RecycleViewMatcher(R.id.historyRecycleView)
                .atPosition(1, R.id.subTitleTextView)
        )
            .check(matches(withText("fact about 10")))

        onView(ViewMatchers.withId(R.id.editText)).perform(typeText("10"))
        closeSoftKeyboard()

        onView(ViewMatchers.withId(R.id.getFactButton)).perform(click())
        onView(RecycleViewMatcher(R.id.historyRecycleView).atPosition(0, R.id.titleTextView))
            .check(matches(withText("10")))
        onView(
            RecycleViewMatcher(R.id.historyRecycleView)
                .atPosition(0, R.id.subTitleTextView)
        )
            .check(matches(withText("fact about 10")))

        onView(RecycleViewMatcher(R.id.historyRecycleView).atPosition(1, R.id.titleTextView))
            .check(matches(withText("11")))
        onView(
            RecycleViewMatcher(R.id.historyRecycleView)
                .atPosition(1, R.id.subTitleTextView)
        )
            .check(matches(withText("fact about 11")))
    }

}