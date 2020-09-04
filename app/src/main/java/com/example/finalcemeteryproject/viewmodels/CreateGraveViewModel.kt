package com.example.finalcemeteryproject.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.finalcemeteryproject.data.CemeteryRepository
import com.example.finalcemeteryproject.data.Grave
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.sql.RowId

class CreateGraveViewModel(application: Application, val repository: CemeteryRepository): AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _rowId = MutableLiveData<Int>()
    val grave: LiveData<Grave> = Transformations.switchMap(_rowId){
        repository.getGraveWithRowId(it)
    }

    fun setRowId(rowId: Int){
        _rowId.value = rowId
    }

    fun insertGrave(grave: Grave){
        viewModelScope.launch {
            repository.insertGrave(grave)
        }
    }
}