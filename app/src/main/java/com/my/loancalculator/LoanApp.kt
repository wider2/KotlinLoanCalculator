package com.my.loancalculator

import android.app.Application
import android.util.Log
import com.my.loancalculator.dao.LoanDatabase

class LoanApp : Application() {
    lateinit var loanDatabase : LoanDatabase

    override fun onCreate() {
        super.onCreate()

        loanDatabase = LoanDatabase.invoke(applicationContext)
        val isDbOpened: Boolean = loanDatabase.isOpen

        Log.d(TAG,"app_id: " + loanDatabase.isOpen)
        if (BuildConfig.DEBUG && isDbOpened) {
            loanDatabase.clearAllTables()
        }
    }

    companion object {
        lateinit var loanApp: LoanApp
        var TAG: String = LoanApp::class.java.simpleName
    }
}