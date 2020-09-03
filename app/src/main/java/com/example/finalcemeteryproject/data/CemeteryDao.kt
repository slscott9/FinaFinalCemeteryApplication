package com.example.finalcemeteryproject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface CemeteryDao {

    @Query("select * from final_cemetery_table")
    fun getAllCemeteries(): LiveData<List<Cemetery>>
}