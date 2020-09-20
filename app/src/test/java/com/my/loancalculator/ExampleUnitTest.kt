package com.my.loancalculator

import androidx.test.filters.SmallTest
import com.my.loancalculator.utils.Calculation
import org.junit.Test

import org.junit.Assert.*

@SmallTest
class ExampleUnitTest {

    @Test
    fun testCalculateCreditScore() {
        val creditScore = Calculation().calcCreditScore(100, 2500.0, 12)
        assertTrue(creditScore < 1)
    }
    @Test
    fun testCalculateCreditScoreNegative() {
        val creditScore = Calculation().calcCreditScore(300, 2500.0, 12)
        assertFalse(creditScore < 1)
    }

    @Test
    fun testCalculateCreditScoreMinAmount() {
        val creditScore = Calculation().calcCreditScore(300, 4000.0, 12)
        assertTrue(creditScore <= 0.9)
    }
    @Test
    fun testCalculateCreditScoreNegative2() {
        val creditScore = Calculation().calcCreditScore(300, 5000.0, 24)
        assertFalse(creditScore < 1)
    }

    @Test
    fun testCalculateCreditScoreOverhead() {
        val creditScore = Calculation().calcCreditScore(1000, 3000.0, 12)
        assertTrue(creditScore > 1)
    }

    @Test
    fun testCalculateMaxAmount() {
        val maxAmount = Calculation().findMaxPossibleAmount(100, 2000.0, 33)
        assertTrue(maxAmount == 3301.0)
    }

    @Test
    fun testCalculateMaxAmount2() {
        val maxAmount = Calculation().findMaxPossibleAmount(300, 5000.0, 24)
        assertTrue(maxAmount == 7201.0)
    }

    @Test
    fun testCalculateMaxAmount3() {
        val maxAmount = Calculation().findMaxPossibleAmount(300, 3000.0, 12)
        assertTrue(maxAmount == 3601.0)
    }

    @Test
    fun testCalculateMinPossibleAmount() {
        val maxAmount = Calculation().findMinPossibleAmount(300, 4000.0, 12)
        assertTrue(maxAmount == 3599.0)
    }

    @Test
    fun testCalculateMinPossiblePeriod() {
        val minPeriod = Calculation().findMinPossiblePeriod(30, 4000.0, 12)
        assertTrue(minPeriod == 135)
    }

    @Test
    fun testCalculateMinPossiblePeriod2() {
        val minPeriod = Calculation().findMinPossiblePeriod(100, 3000.0, 12)
        assertTrue(minPeriod == 31)
    }

}