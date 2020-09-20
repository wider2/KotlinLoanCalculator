package com.my.loancalculator

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    lateinit var appContext: Context

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @After
    fun tearDown() {
        activityTestRule.finishActivity()
    }

    @Test
    fun goThroughTest() {
        val notifiedText = appContext.getString(R.string.errorPersonalCode)
        if (doesViewExist(R.id.buttonIdCode)) {
            val signInButton = onView(withId(R.id.buttonIdCode)).check(matches(isDisplayed()))
            signInButton.perform(click())

            onView(withId(R.id.textViewNotification)).check(matches(withText(notifiedText)))

            val editText =
                onView(
                    allOf(
                        withId(R.id.editTextIdCode),
                        withTagValue(`is`("tagLoginEditText" as Any))
                    )
                )

            editText.perform(typeText(STRING_TO_BE_TYPED))

            onView(withId(R.id.buttonIdCode)).check(matches(isDisplayed())).perform(click())
            Thread.sleep(1000)
            //onView(allOf(withTagValue(`is`("tagButtonIdCode" as Any)), isDisplayed())).perform(click())
            onView(withId(R.id.buttonSignOut)).check(matches(isDisplayed())).perform(click())

            editText.perform(replaceText(STRING_TO_BE_TYPED2))

            onView(withId(R.id.buttonIdCode)).check(matches(isDisplayed())).perform(click())
            Thread.sleep(1000)


            val amountEditText =
                onView(
                    allOf(
                        withId(R.id.editTextLoanAmount),
                        withTagValue(`is`("tagEditTextLoanAmount" as Any))
                    )
                )
            amountEditText.perform(replaceText("1000"))

            onView(withId(R.id.buttonSubmit)).check(matches(isDisplayed())).perform(click())
            Thread.sleep(1000)
            amountEditText.perform(replaceText("3000"))

            val periodEditText =
                onView(
                    allOf(
                        withId(R.id.editTextLoanPeriod),
                        withTagValue(`is`("tagEditTextLoanPeriod" as Any))
                    )
                )
            periodEditText.perform(replaceText("20"))
            onView(withId(R.id.buttonSubmit)).check(matches(isDisplayed())).perform(click())
            Thread.sleep(3000)
        }
    }

    //androidx.test.espresso.NoActivityResumedException: No activities in stage RESUMED. Did you forget to launch the activity.
    //Make sure your device is on and app/test is able to run in foreground, not turned off
    fun doesViewExist(id: Int): Boolean {
        return try {
            onView(withId(id)).check(matches(isDisplayed()))
            true
        } catch (e: NoMatchingViewException) {
            false
        }
    }

    companion object {
        val STRING_TO_BE_TYPED = "49002010965"
        val STRING_TO_BE_TYPED2 = "49002010976"
        val TAG = "EspressoTest"
    }

}