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

    init {
        refreshVideos()
    }

    fun refreshVideos(){
            repository.refreshVideos(){
                if(it == null){
                    _responseFailure.value = false //onFailure to get cemetery list set the failure message and toast in activitty
                }else{
                    viewModelScope.launch {
                        repository.insertNetworkCems(it) //onSucess insert the cemteries into database
                        Log.i("CemeteryListViewModel", "List received and inserted into database")

                    }
                }
            } // view model created get all cems from network insert into database

    }


    private val _responseFailure = MutableLiveData<Boolean>()
    val responseFailure: LiveData<String> = Transformations.map(_responseFailure){
        if(it) "Successfully got cemeteries from network" else "Failed to get cemeteries from network"
    }


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