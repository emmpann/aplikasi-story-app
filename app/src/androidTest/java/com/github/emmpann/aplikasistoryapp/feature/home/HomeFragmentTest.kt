package com.github.emmpann.aplikasistoryapp.feature.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import com.github.emmpann.aplikasistoryapp.R
import com.github.emmpann.aplikasistoryapp.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun logoutUser_Success() {
        launchFragmentInHiltContainer<HomeFragment>()

        onView(withId(R.id.menu_logout)).check(matches(isDisplayed()))
        onView(withId(R.id.menu_logout)).perform(click())

        onView(withId(android.R.id.button1))
            .inRoot(isDialog())
            .check(matches(withText(R.string.yes)))
            .check(matches(isDisplayed()))

        onView(withId(android.R.id.button1)).perform(click())

    }
}