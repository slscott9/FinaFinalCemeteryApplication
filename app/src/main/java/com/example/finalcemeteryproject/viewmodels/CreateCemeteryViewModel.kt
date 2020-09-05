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




        var newCemeteryKey: Int? = null

         init {
             viewModelScope.launch {
                 newCemeteryKey = repository.getMaxCemeteryRowNum() ?: 0 //needs to be in couroutine launch
             }

         }





    fun insertNewCemetery(cemetery: Cemetery){
        viewModelScope.launch {
            newCemeteryKey = newCemeteryKey?.plus(1)
            Log.i("CreateViewModel", "the new cemetery key is $newCemeteryKey")
            repository.insertCemetery(cemetery)
        }
    }

    fun sendNeCemeteryToNetwork(cemetery: Cemetery){
        viewModelScope.launch {
            repository.newWaySendCemeteryToNetwork(cemetery)
        }
    }



    ///need internet connection
}