package com.example.finalcemeteryproject.data

import androidx.lifecycle.LiveData

class CemeteryRepository(private val cemeteryDao: CemeteryDao) {

    fun getAllCemeteries() : LiveData<List<Cemetery>> {
        return cemeteryDao.getAllCemeteries()
    }

}