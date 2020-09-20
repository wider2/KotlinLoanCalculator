package com.my.loancalculator.utils

class OverridableLazy<T>(var implementation: Lazy<T>): Lazy<T> {
    override val value
        get() = implementation.value
    override fun isInitialized() = implementation.isInitialized()
}