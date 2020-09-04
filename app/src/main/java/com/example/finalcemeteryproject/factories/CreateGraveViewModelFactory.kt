package com.example.finalcemeteryproject.factories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalcemeteryproject.data.CemeteryRepository
import com.example.finalcemeteryproject.viewmodels.CreateCemeteryViewModel
import com.example.finalcemeteryproject.viewmodels.CreateGraveViewModel

class CreateGraveViewModelFactory(
    private val application: Application,
    private val repository: CemeteryRepository,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateGraveViewModel::class.java)) {
            return CreateGraveViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}