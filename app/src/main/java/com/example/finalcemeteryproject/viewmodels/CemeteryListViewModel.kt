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
//        refreshVideos()
    }

    /*
        The callback with enqueue signals when a failure or a reponse  happened implementing the callback with a lambda
        Allows for completing actions based on a failure or a response
     */
    fun refreshVideos(){
            repository.refreshCemeteryList(){
                if(it == null){
                    _responseFailure.value = false //if the response failed set the error message
                }else{
                    viewModelScope.launch {
                        repository.insertNetworkCems(it) //if the reponse is good insert into database
                        Log.i("Doing work in view model", "work")

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