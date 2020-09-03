package com.example.finalcemeteryproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.finalcemeteryproject.R
import com.example.finalcemeteryproject.data.CemeteryRepository
import com.example.finalcemeteryproject.data.CemeteryRoomDatabase
import com.example.finalcemeteryproject.databinding.ActivityCemeteryListBinding
import com.example.finalcemeteryproject.factories.CemeteryListViewModelFactory
import com.example.finalcemeteryproject.viewmodels.CemeteryListViewModel

class CemeteryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCemeteryListBinding
    private lateinit var cemeteryListViewModel: CemeteryListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cemetery_list)
        binding.lifecycleOwner = this

        initViewModel()




    }

    private fun initViewModel(){
        val cemeteryDao = CemeteryRoomDatabase.getDatabase(applicationContext).cemDao()
        val repository = CemeteryRepository(cemeteryDao)
        val viewModelFactory = CemeteryListViewModelFactory(application, repository)
        cemeteryListViewModel = ViewModelProvider(this, viewModelFactory).get(CemeteryListViewModel::class.java)
    }
}

