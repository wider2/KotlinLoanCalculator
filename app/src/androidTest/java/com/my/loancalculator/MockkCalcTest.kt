package com.my.loancalculator

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.my.loancalculator.utils.Calculation
import io.mockk.*
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MockkCalcTest {

    val mockCalc = mockk<Calculation>()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testSpyCalc() {
        val spyCalc = spyk(Calculation())

        val result = 0.6
        every { spyCalc.calcCreditScore(100, 2000.0, 12) } returns 0.6

        val creditScore = spyCalc.calcCreditScore(100, 2000.0, 12)
        assertThat(creditScore, IsEqual(result))
    }

    @Test
    fun testMockCalcPeriod() {
        val result = 31
        every { mockCalc.findMinPossiblePeriod(100, 3000.0, 12) } returns result
        val resultPeriod = mockCalc.findMinPossiblePeriod(100, 3000.0, 12)
        assertEquals(result, resultPeriod)
    }

    @Test
    fun testMockCalcAmount() {
        val result = 3599.0
        every { mockCalc.findMinPossibleAmount(300, 4000.0, 12) } answers { result }

        val amount = mockCalc.findMinPossibleAmount(300, 4000.0, 12)
        assertThat(result, IsEqual(amount))
    }

    @Test
    fun testException() {
        val currentException = "Division by zero"
        try {
            every {
                mockCalc.findMinPossiblePeriod(
                    100,
                    0.0,
                    12
                )
            } throws AssertionError(currentException) //ArithmeticException(currentException)
        } catch (ex: ArithmeticException) {
            assertEquals(currentException, "${ex.message}")
        }
    }
}