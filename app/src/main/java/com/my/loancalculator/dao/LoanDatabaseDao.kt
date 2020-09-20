package app.receipt.dao

import androidx.room.*
import com.my.loancalculator.entity.EntityProfile

// SQL CRUD operation for Google Room
@Dao
interface LoanDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(products: EntityProfile)

    @Query("SELECT * from profile_table WHERE id= :id")
    fun getItemById(id: Int) : List<EntityProfile>

    @Query("SELECT * from profile_table")
    fun getAll(): List<EntityProfile>

    @Query("DELETE FROM profile_table")
    fun clearProfile()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfiles(profile: List<EntityProfile>)

    @Query("SELECT * FROM profile_table WHERE personalCode= :personalCode")
    fun getProfileByIdCode(personalCode: String) : EntityProfile

}