package com.example.finalcemeteryproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.finalcemeteryproject.R
import com.example.finalcemeteryproject.adapters.GraveListAdapter
import com.example.finalcemeteryproject.adapters.GraveListListener
import com.example.finalcemeteryproject.data.CemeteryRepository
import com.example.finalcemeteryproject.data.CemeteryRoomDatabase
import com.example.finalcemeteryproject.databinding.ActivityCemeteryDetailBinding
import com.example.finalcemeteryproject.factories.CemeteryDetailViewModelFactory
import com.example.finalcemeteryproject.viewmodels.CemeteryDetailViewModel

class CemeteryDetailActivity : AppCompatActivity() {

    private lateinit var cemeteryDetailViewModel: CemeteryDetailViewModel
    private lateinit var binding: ActivityCemeteryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cemetery_detail)
        binding.lifecycleOwner = this
        val cemeteryId = intent.getIntExtra(CEMETERY_ID, 0)
        Log.i("CemeteryDetailActivity", "The cemetery id is $cemeteryId")

        initViewModel(cemeteryId)

        binding.cemeteryDetailViewModel = cemeteryDetailViewModel

        val graveListAdapter = GraveListAdapter(GraveListListener {
            val intent = Intent(this, GraveDetailActivity::class.java)
            intent.putExtra(GraveDetailActivity.GRAVE_ROW_ID, it)
            startActivity(intent)

        })

        cemeteryDetailViewModel.cemeterysGraves.observe(this, Observer {
            it?.let {
                graveListAdapter.submitList(it)
            }
        })

        binding.graveRecyclerView.adapter = graveListAdapter

        binding.addChip.setOnClickListener {
            val intent = Intent(this, CreateGraveActivity::class.java)
            intent.putExtra("cemetery_id", cemeteryId)
            startActivity(intent)
        }


    }

    private fun initViewModel(cemeteryId: Int){
        val cemeteryDao = CemeteryRoomDatabase.getDatabase(application).cemDao()
        val repository = CemeteryRepository(cemeteryDao)
        val viewModelFactory = CemeteryDetailViewModelFactory(application, repository, cemeteryId)
        cemeteryDetailViewModel = ViewModelProvider(this, viewModelFactory).get(CemeteryDetailViewModel::class.java)
    }


    companion object {
        val CEMETERY_ID = "cemetery_id"
    }
}