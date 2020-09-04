package com.example.finalcemeteryproject.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.sql.RowId

@Dao
interface CemeteryDao {

    @Query("select * from final_cemetery_table")
    fun getAllCemeteries(): LiveData<List<Cemetery>>

    @Insert
    suspend fun insertCemetery(cemetery: Cemetery)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrave(grave: Grave)

    @Query("delete from final_graves_table where id= :rowId")
    suspend fun deleteGrave(rowId: Int)

    @Query("select * from final_graves_table where id=  :rowId")
    fun getGraveWithRowid(rowId: Int): LiveData<Grave>

    @Query("select * from final_cemetery_table where id= :cemeteryId")
     fun getCemeteryWithId(cemeteryId: Int): LiveData<Cemetery>


    @Query("select * from final_graves_table where cemeteryId= :cemeteryId")
    fun getGravesWithCemeteryId(cemeteryId: Int) : LiveData<List<Grave>>
}