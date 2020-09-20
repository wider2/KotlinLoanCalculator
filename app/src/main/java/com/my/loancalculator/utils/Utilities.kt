package com.my.loancalculator.utils

class Utilities {

    fun convertToInt(nullableInt: Int?): Int {
        return nullableInt ?: 0
    }
    fun convertToDouble(nullableDouble: Double?): Double {
        return nullableDouble ?: 0.0
    }

    fun convertToInt(str: String) {
        val parsedInt = str.toIntOrNull()
        if (parsedInt != null) {
            println("The parsed int is $parsedInt")
        } else {
            // not a valid int
        }
    }
}
