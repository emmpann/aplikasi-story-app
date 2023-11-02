package com.github.emmpann.aplikasistoryapp.feature.login

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.github.emmpann.aplikasistoryapp.R
import com.github.emmpann.aplikasistoryapp.feature.home.HomeFragment
import com.github.emmpann.aplikasistoryapp.launchFragmentInHiltContainer
import com.github.emmpann.aplikasistoryapp.util.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
@LargeTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class LoginFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun loginUser_Success() {

        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<LoginFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.emailEditText)).perform(ViewActions.typeText("mhdepan@gmail.com"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.passwordEditText)).perform(ViewActions.typeText("anjay123"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.loginButton)).perform(click())

        show_HomeFragment()
    }

    private fun show_HomeFragment() {
        launchFragmentInHiltContainer<HomeFragment>()
    }
}