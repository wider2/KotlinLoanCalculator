package com.my.loancalculator

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import app.receipt.dao.LoanDatabaseDao
import com.my.loancalculator.dao.LoanDatabase
import com.my.loancalculator.entity.EntityProfile
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException

class RoomTest {
    private lateinit var loanDao: LoanDatabaseDao
    private lateinit var db: LoanDatabase
    private lateinit var profile: EntityProfile

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, LoanDatabase::class.java).build()
        loanDao = db.loanDatabaseDao
        profile = EntityProfile(1, "49002010965", "Olmer", true, 0)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        if (db.isOpen()) db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testInsertToDatabase() {
        loanDao.clearProfile()
        loanDao.insertProfile(profile)

        val resultProfile = loanDao.getItemById(1)

        assertNotNull(resultProfile)
        assertTrue(resultProfile.isNotEmpty())
        assertEquals(profile.personalCode, resultProfile.get(0).personalCode)
    }

    @Test
    @Throws(Exception::class)
    fun testInsertToDatabaseAndCompare() {
        val personalCode = "49002010965"
        loanDao.insertProfile(profile)

        val result = loanDao.getProfileByIdCode(personalCode)
        assertNotNull(result)
        assertThat(personalCode, equalTo(result.personalCode))
    }
}