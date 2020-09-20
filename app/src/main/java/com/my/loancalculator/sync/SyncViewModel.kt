package com.my.loancalculator.sync

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.my.loancalculator.Event
import com.my.loancalculator.R
import com.my.loancalculator.dao.LoanDatabase
import com.my.loancalculator.domain.SyncUseCase
import com.my.loancalculator.entity.EntityProfile
import com.my.loancalculator.model.ProfileResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import kotlin.collections.ArrayList

class SyncViewModel(
    private val syncUseCase: SyncUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var context: Context

    fun setContext(ctx: Context) {
        context = ctx
    }

    private val _showSync = MutableLiveData<Event<String>>()
    val showSync: LiveData<Event<String>> = _showSync

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
        Log.i(TAG, "ViewModel destroyed")
    }

    /*
    * get Profiles of users we will work with
    */
    fun getSyncProfiles() {
        compositeDisposable.add(
            syncUseCase()
                .subscribe({
                    handleResponse(it)
                }, {
                    handleError(it)
                })
        )
    }

    fun handleResponse(list: ArrayList<ProfileResponse>) {
        compositeDisposable.add(
            Observable.fromCallable<Boolean> {
                val entityProfile: ArrayList<EntityProfile> = ArrayList()
                list.forEach {
                    entityProfile.add(
                        EntityProfile(
                            it.id,
                            it.personalCode,
                            it.fullName,
                            it.debt,
                            it.creditModifier
                        )
                    )
                }
                //fill Room database with users data
                val loanDatabase = LoanDatabase.invoke(context)
                loanDatabase.loanDatabaseDao.clearProfile()
                loanDatabase.loanDatabaseDao.insertProfiles(entityProfile)

                val result = loanDatabase.loanDatabaseDao.getItemById(1)
                if (!result.get(0).personalCode.equals("")) return@fromCallable true else return@fromCallable false
            }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    handleResponseFinished(it)
                }, {
                    handleError(it)
                })
        )
    }

    private fun handleError(error: Throwable) {
        Log.d(TAG, error.localizedMessage)
        _showSync.value = Event(error.message ?: context.getString(R.string.errorUnknown))
    }

    private fun handleResponseFinished(result: Boolean) {
        //_showSync.value = Event(context.getString(R.string.syncSuccess))
        Log.d(TAG, context.getString(R.string.dataSynchronized) + result)
    }

    companion object {
        private const val TAG = "SyncViewModel"
    }
}