package com.example.finalcemeteryproject.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalcemeteryproject.data.Cemetery
import com.example.finalcemeteryproject.data.CemeteryRepository
import com.example.finalcemeteryproject.data.Grave
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CemeteryDetailViewModel(application: Application, repository: CemeteryRepository, cemeteryId: Int) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _cemeteryWithId = repository.getCemeteryWithId(cemeteryId)
    val cemeteryWithId: LiveData<Cemetery> = _cemeteryWithId


    private val _cemeterysGraves = repository.getGravesWithCemeteryId(cemeteryId)
    val cemeterysGraves : LiveData<List<Grave>> = _cemeterysGraves


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}


