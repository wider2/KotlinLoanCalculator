package com.my.loancalculator.sync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.my.loancalculator.domain.SyncUseCase

@Suppress("UNCHECKED_CAST")
class SyncViewModelFactory(

) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SyncViewModel::class.java)) {
            return SyncViewModel(
                SyncUseCase()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}