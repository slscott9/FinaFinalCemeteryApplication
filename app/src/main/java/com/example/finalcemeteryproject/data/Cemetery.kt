package com.example.finalcemeteryproject.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "final_cemetery_table")
data class Cemetery(
//    @PrimaryKey(autoGenerate = false) //for when we receive cemeteries from the network
//    @ColumnInfo(name = "row_number")
//    val id: Int = 100,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val cemeteryName: String,

    val cemeteryLocation: String,

    val cemeteryState: String,

    val cemeteryCounty: String,

    val township: String,

    val range: String,

    val spot: String,

    val firstYear: String,

    val section: String

)


@Entity(tableName = "final_graves_table")
data class Grave(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val cemeteryId: Int,

    val firstName: String,

    val lastName: String,

    val birthDate: String,

    val deathDate: String,

    val marriageYear: String,

    val comment: String,

    val graveNumber: String
)