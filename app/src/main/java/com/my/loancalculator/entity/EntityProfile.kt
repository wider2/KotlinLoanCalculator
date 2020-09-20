package com.my.loancalculator.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class EntityProfile(

       @PrimaryKey
       var id: Int,

       @ColumnInfo(name = "personalCode")
       var personalCode: String,

       @ColumnInfo(name = "fullName")
       var fullName: String,

       @ColumnInfo(name = "debt")
       var debt: Boolean,

       @ColumnInfo(name = "creditModifier")
       var creditModifier: Int
)
