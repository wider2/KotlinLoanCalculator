package com.my.loancalculator.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.receipt.dao.LoanDatabaseDao
import com.my.loancalculator.entity.EntityProfile

@Database(
    entities = [
        EntityProfile::class
    ], version = 9, exportSchema = false
)
abstract class LoanDatabase : RoomDatabase() {

    abstract val loanDatabaseDao: LoanDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: LoanDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            LoanDatabase::class.java, "DatabaseLoan.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
