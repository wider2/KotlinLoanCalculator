package com.my.loancalculator.utils

interface ICalculation {
    fun calcCreditScore(creditModifier: Int, loanAmount: Double, loanPeriod: Int): Double
    fun findMaxPossibleAmount(creditModifier: Int, loanAmount: Double, loanPeriod: Int): Double
    fun findMinPossibleAmount(creditModifier: Int, loanAmount: Double, loanPeriod: Int): Double
    fun findMinPossiblePeriod(creditModifier: Int, loanAmount: Double, loanPeriod: Int): Int
}
