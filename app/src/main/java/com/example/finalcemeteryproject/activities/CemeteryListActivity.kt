package com.example.finalcemeteryproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.finalcemeteryproject.R
import com.example.finalcemeteryproject.adapters.CemeteryListAdapter
import com.example.finalcemeteryproject.adapters.CemeteryListener
import com.example.finalcemeteryproject.data.Cemetery
import com.example.finalcemeteryproject.data.CemeteryRepository
import com.example.finalcemeteryproject.data.CemeteryRoomDatabase
import com.example.finalcemeteryproject.databinding.ActivityCemeteryListBinding
import com.example.finalcemeteryproject.factories.CemeteryListViewModelFactory
import com.example.finalcemeteryproject.viewmodels.CemeteryListViewModel

class CemeteryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCemeteryListBinding
    private lateinit var cemeteryListViewModel: CemeteryListViewModel
    private lateinit var cemeteryListAdapter: CemeteryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cemetery_list)
        binding.lifecycleOwner = this

        initViewModel()

        cemeteryListAdapter = CemeteryListAdapter(CemeteryListener {
            val intent = Intent(this, CemeteryDetailActivity::class.java)
            intent.putExtra(CemeteryDetailActivity.CEMETERY_ID, it)
            startActivity(intent)
        })

        cemeteryListViewModel.allCemeteries.observe(this, Observer {
            it?.let {
                cemeteryListAdapter.submitList(it)
            }
        })

        binding.addCemeteryFAB.setOnClickListener {
            val intent = Intent(this, CreateCemeteryActivity::class.java)
            startActivity(intent)
        }


        binding.cemeteryListRecyclerView.adapter = cemeteryListAdapter


        val cemetery1 = Cemetery(
            cemeteryName = "Thorsby Cemetery",
            cemeteryLocation = "Thorsby",
            cemeteryState = "Alabama",
            cemeteryCounty = "Chilton County",
            township = "t",
            range = "r",
            spot = "s",
            firstYear = "1800",
            section = "t section"
        )


//        cemeteryListViewModel.insertCemetery(cemetery1)

    }

    private fun initViewModel(){
        val cemeteryDao = CemeteryRoomDatabase.getDatabase(applicationContext).cemDao()
        val repository = CemeteryRepository(cemeteryDao)
        val viewModelFactory = CemeteryListViewModelFactory(application, repository)
        cemeteryListViewModel = ViewModelProvider(this, viewModelFactory).get(CemeteryListViewModel::class.java)
    }
}

