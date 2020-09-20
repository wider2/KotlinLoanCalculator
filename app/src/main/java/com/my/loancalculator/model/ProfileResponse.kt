package com.my.loancalculator.model

data class ProfileResponse(
    val id: Int,
    val personalCode: String,
    val fullName: String,
    val debt: Boolean,
    val creditModifier: Int
)
