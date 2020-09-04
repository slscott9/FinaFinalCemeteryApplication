package com.example.finalcemeteryproject.data

import androidx.lifecycle.LiveData

class CemeteryRepository(private val cemeteryDao: CemeteryDao) {

    fun getAllCemeteries() : LiveData<List<Cemetery>> {
        return cemeteryDao.getAllCemeteries()
    }


    suspend fun insertCemetery(cemetery: Cemetery){
        cemeteryDao.insertCemetery(cemetery)
    }

    suspend fun deleteGrave(rowid: Int){
        cemeteryDao.deleteGrave(rowid)
    }

    suspend fun insertGrave(grave: Grave){
        cemeteryDao.insertGrave(grave)
    }

    fun getGraveWithRowId(rowid: Int): LiveData<Grave>{
        return cemeteryDao.getGraveWithRowid(rowid)
    }

    fun getCemeteryWithId(cemeteryId: Int): LiveData<Cemetery>{
        return cemeteryDao.getCemeteryWithId(cemeteryId)
    }

    fun getGravesWithCemeteryId(cemeteryId: Int): LiveData<List<Grave>>{
        return cemeteryDao.getGravesWithCemeteryId(cemeteryId)
    }

}