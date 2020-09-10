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
import java.io.IOException

class CemeteryListViewModel(application: Application, private val repository: CemeteryRepository) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)



    private var _networkCemeteryListResponse = repository.networkCemeteryListResponse
    val eventNetworkError: LiveData<String> = Transformations.map(_networkCemeteryListResponse){
        if(it) "Successfully retrieved cemetery list from network" else "Failed to retrieve cemetery list from network"
    }


    init {
        refreshCemeteryList() //gets list from network inserts into data base
    }

    private val _cemeteryList = repository.cemeteryDatabaseList
    val cemeteryList: LiveData<List<Cemetery>> = _cemeteryList



    private fun refreshCemeteryList(){
        viewModelScope.launch {
            repository.refreshCemeteryList()
        }
    }

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