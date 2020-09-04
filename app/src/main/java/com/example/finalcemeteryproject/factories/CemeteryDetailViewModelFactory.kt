package com.example.finalcemeteryproject.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalcemeteryproject.data.CemeteryRepository
import com.example.finalcemeteryproject.viewmodels.CemeteryDetailViewModel
import com.example.finalcemeteryproject.viewmodels.CemeteryListViewModel

class CemeteryDetailViewModelFactory(
    private val application: Application,
    private val repository: CemeteryRepository,
    private val cemeteryId: Int
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CemeteryDetailViewModel::class.java)) {
            return CemeteryDetailViewModel(application, repository, cemeteryId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}