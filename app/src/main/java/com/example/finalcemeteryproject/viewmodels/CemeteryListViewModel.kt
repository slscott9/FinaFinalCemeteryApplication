package com.example.finalcemeteryproject.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.finalcemeteryproject.data.Cemetery
import com.example.finalcemeteryproject.data.CemeteryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CemeteryListViewModel(application: Application, private val repository: CemeteryRepository) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _allCemeteries = repository.getAllCemeteries()
    val allCemeteries: LiveData<List<Cemetery>> = _allCemeteries

    
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