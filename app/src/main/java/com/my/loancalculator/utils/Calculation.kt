package com.my.loancalculator.utils

import com.my.loancalculator.MAXIMUM_AMOUNT
import com.my.loancalculator.MINIMUM_AMOUNT

open class Calculation: ICalculation {

    override fun calcCreditScore(creditModifier: Int, loanAmount: Double, loanPeriod: Int): Double {
        return ((creditModifier / loanAmount) * loanPeriod)
    }

    override fun findMaxPossibleAmount(creditModifier: Int, loanAmount: Double, loanPeriod: Int): Double {
        var creditScore: Double = 1.0
        var curAmount: Double = loanAmount

        while (creditScore >= 1) {
            if (curAmount > MAXIMUM_AMOUNT) {
                curAmount = MAXIMUM_AMOUNT
                break
            }
            creditScore = calcCreditScore(creditModifier, curAmount, loanPeriod)
            if (creditScore < 1) break
            curAmount += 1
        }
        return curAmount
    }

    override fun findMinPossibleAmount(creditModifier: Int, loanAmount: Double, loanPeriod: Int): Double {
        var creditScore: Double = 0.0
        var curAmount: Double = loanAmount

        while (creditScore < 1) {
            if (curAmount < MINIMUM_AMOUNT) {
                curAmount = 0.0
                break
            }
            creditScore = calcCreditScore(creditModifier, curAmount, loanPeriod)
            curAmount -= 1
        }
        return curAmount
    }
    override fun findMinPossiblePeriod(creditModifier: Int, loanAmount: Double, loanPeriod: Int): Int {
        var creditScore: Double = 0.0
        var curPeriod: Int = loanPeriod

        while (creditScore < 1) {
            creditScore = calcCreditScore(creditModifier, loanAmount, curPeriod)
            curPeriod += 1
        }
        return curPeriod
    }
}
