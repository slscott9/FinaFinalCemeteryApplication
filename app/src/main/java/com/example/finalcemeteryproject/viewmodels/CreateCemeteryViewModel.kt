package com.example.finalcemeteryproject.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.finalcemeteryproject.data.Cemetery
import com.example.finalcemeteryproject.data.CemeteryRepository
import kotlinx.coroutines.*


class CreateCemeteryViewModel(application: Application, val repository: CemeteryRepository) : AndroidViewModel(application){

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope( Dispatchers.Main + viewModelJob )

        var newCemeteryKey: Int = 0

         init {
             viewModelScope.launch {
                 val tempMax = repository.getMaxCemeteryRowNum()
                 newCemeteryKey = tempMax  ?: 0                          //if max row num is null (database is empty) then max is 0
                 newCemeteryKey += 1                                      //if max is not null it equal highest row number add 1 for the next insert to be correct

             }
         }

//    fun sendNeCemeteryToNetwork(cemetery: Cemetery){
//        viewModelScope.launch {
//            repository.newWaySendCemeteryToNetwork(cemetery){
//                _responseFailure.value = it == null
//            }
//        }
//    }

    private val _responseFailure = MutableLiveData<Boolean>()
    val responseFailure: LiveData<String> = Transformations.map(_responseFailure){
        if(it) "Successfully sent cemetery to network" else "Failed to send cemetery to network"
    }


    fun insertNewCemetery(cemetery: Cemetery){
        viewModelScope.launch {
            newCemeteryKey += 1
            repository.insertCemetery(cemetery)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}