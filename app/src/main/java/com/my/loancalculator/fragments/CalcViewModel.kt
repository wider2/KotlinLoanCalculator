package com.my.loancalculator.fragments

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.my.loancalculator.Event
import com.my.loancalculator.R
import com.my.loancalculator.dao.LoanDatabase
import com.my.loancalculator.entity.EntityProfile
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CalcViewModel() : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var context: Context
    private lateinit var loanDatabase: LoanDatabase

    fun setContext(ctx: Context) {
        context = ctx
        loanDatabase = LoanDatabase.invoke(context)
    }

    init {
    }

    private val _showItemsError = MutableLiveData<Event<String>>()
    val showItemsError: LiveData<Event<String>> = _showItemsError

    private val _profile = MutableLiveData<Event<EntityProfile>>()
    val profile: LiveData<Event<EntityProfile>> = _profile

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
        Log.i(TAG, "ViewModel destroyed")
    }

    fun getProfileByIdCode(idCode: String) {
        compositeDisposable.add(
            Observable.fromCallable<EntityProfile> {
                val profile = loanDatabase.loanDatabaseDao.getProfileByIdCode(idCode)
                return@fromCallable profile
            }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    handleProfileResponse(it)
                }, {
                    handleError(it)
                })
        )
    }

    private fun handleProfileResponse(profile: EntityProfile) {
        _profile.value = Event(profile)
    }

    private fun handleError(error: Throwable) {
        Log.d(TAG, error.localizedMessage)
        if (error.message.equals("Callable returned null")) {
            _showItemsError.value = Event(context.getString(R.string.errorPersonalCode))
        } else {
            _showItemsError.value = Event(error.message ?: context.getString(R.string.errorUnknown))
        }
    }

    companion object {
        private const val TAG = "CalcViewModel"
    }
}