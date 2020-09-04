package com.example.finalcemeteryproject.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.finalcemeteryproject.data.Cemetery
import com.example.finalcemeteryproject.data.CemeteryRepository
import kotlinx.coroutines.*

class CreateCemeteryViewModel(application: Application, val repository: CemeteryRepository) : AndroidViewModel(application){

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope( Dispatchers.Main + viewModelJob )


    fun insertNewCemetery(cemetery: Cemetery){
        viewModelScope.launch {
            repository.insertCemetery(cemetery)
        }
    }
}