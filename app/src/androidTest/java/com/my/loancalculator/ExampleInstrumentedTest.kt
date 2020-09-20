package com.my.loancalculator

import android.content.Context
import android.widget.TextView
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.my.loancalculator.dao.LoanDatabase
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    lateinit var appContext: Context
    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Before
    fun setup() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        assertEquals("com.my.loancalculator", appContext.packageName)
    }

    @After
    fun tearDown() {
        activityTestRule.finishActivity()
    }

    @Test
    fun testDatabase() {
        val loanDatabase = LoanDatabase.invoke(appContext)
        assertNotNull(loanDatabase)
        loanDatabase.clearAllTables()
        assertTrue(loanDatabase.isOpen)
    }

    @Test
    fun testUiView() {
        val textView = activityTestRule.activity.findViewById<TextView>(R.id.textViewOutput)
        assertThat(textView, notNullValue())
        assertThat(textView, instanceOf(TextView::class.java))
    }

}