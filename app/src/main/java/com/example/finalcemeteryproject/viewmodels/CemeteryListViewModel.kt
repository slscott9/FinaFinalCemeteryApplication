package com.example.finalcemeteryproject.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.finalcemeteryproject.data.Cemetery
import com.example.finalcemeteryproject.data.CemeteryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CemeteryListViewModel(application: Application, private val repository: CemeteryRepository) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _networkCemeteryListFailure = repository.networkCemeteryListFailure
    val networkCemeteryListFailure: LiveData<String> = Transformations.map(_networkCemeteryListFailure){
        if(it) "Failed to retrieve cemeteries from network to send cemeteries" else "Successfully retrieved cemeteries from network"
    }

    init {
        repository.refreshCemeteryList()
    }

    private val _cemeteryList = repository.cemeteryDatabaseList
    val cemeteryList: LiveData<List<Cemetery>> = _cemeteryList


    fun insertCemetery(cemetery: Cemetery){
        viewModelScope.launch {
            repository.insertCemetery(cemetery)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}