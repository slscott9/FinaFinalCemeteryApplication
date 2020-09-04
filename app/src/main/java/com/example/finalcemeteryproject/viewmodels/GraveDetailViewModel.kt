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

class GraveDetailViewModel(application: Application, val repository: CemeteryRepository, graveId: Int) :AndroidViewModel(application){

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)



    private val graveRowId = MutableLiveData<Int>()
    val graveWithId: LiveData<Grave> = Transformations.switchMap(graveRowId){
        rowId -> repository.getGraveWithRowId(rowId)
    }


    //init block placement matters it cannot be above graverRowId because it would try to set it before it was initialized
    init {
        setRowId(graveId)
    }

    fun setRowId(rowId: Int){
        graveRowId.value = rowId
    }

    fun deleteGrave(rowId: Int){
        viewModelScope.launch {
            repository.deleteGrave(rowId)
        }
    }


}