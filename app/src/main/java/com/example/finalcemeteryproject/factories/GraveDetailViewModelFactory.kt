package com.example.finalcemeteryproject.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalcemeteryproject.data.CemeteryRepository
import com.example.finalcemeteryproject.viewmodels.CemeteryDetailViewModel
import com.example.finalcemeteryproject.viewmodels.GraveDetailViewModel

class GraveDetailViewModelFactory(
    private val application: Application,
    private val repository: CemeteryRepository,
    private val graveId: Int
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GraveDetailViewModel::class.java)) {
            return GraveDetailViewModel(application, repository, graveId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}