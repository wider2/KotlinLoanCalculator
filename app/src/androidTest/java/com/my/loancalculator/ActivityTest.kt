package com.my.loancalculator

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
//import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class ActivityTest {

    lateinit var scenario: ActivityScenario<MainActivity>
    lateinit var appContext: Context

    @Before
    fun setup() {
        scenario = launchActivity<MainActivity>()
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @After
    fun cleanup() {
        scenario.close()
    }

    @Test
    fun testEvent() {
        if (::scenario.isInitialized) {
            scenario.moveToState(Lifecycle.State.CREATED)
            scenario.onActivity { activity ->
                assertTrue(activity.textViewOutput.text.equals(""))
            }
            scenario.moveToState(Lifecycle.State.RESUMED)
            assertEquals(Lifecycle.State.RESUMED, scenario.state)
        }
    }

}