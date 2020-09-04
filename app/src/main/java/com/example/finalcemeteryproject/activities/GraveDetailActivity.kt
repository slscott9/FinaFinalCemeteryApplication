package com.example.finalcemeteryproject.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.finalcemeteryproject.R
import com.example.finalcemeteryproject.data.CemeteryRepository
import com.example.finalcemeteryproject.data.CemeteryRoomDatabase
import com.example.finalcemeteryproject.data.Grave
import com.example.finalcemeteryproject.databinding.ActivityGraveDetailBinding
import com.example.finalcemeteryproject.factories.GraveDetailViewModelFactory
import com.example.finalcemeteryproject.viewmodels.GraveDetailViewModel

class GraveDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGraveDetailBinding
    private lateinit var viewModel: GraveDetailViewModel
    private var cemetery_id: Int? = null
    private var graveRowId: Int? = null
    private lateinit var grave: Grave

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_grave_detail)
        binding.lifecycleOwner = this

        cemetery_id = intent.getIntExtra("cemetery_id", 0)
        graveRowId = intent.getIntExtra(GRAVE_ROW_ID, -1)

        Log.i("GraveDetailActivity", "graveRowId is ${graveRowId}")
        Log.i("GraveDetailActivity", "cemetery_id is ${cemetery_id}")

        initViewModel(graveRowId!!)


        viewModel.graveWithId.observe(this, Observer {
            it?.let{
                binding.grave = it
                grave =  it
            }
        })


        binding.deleteChip.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Are you sure you want to delete this grave?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteGrave(graveRowId!!)              //have to use the grave row id from the table to delete the right grave
                    finish()
                }
                .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.cancel()
                })
            val alert = dialogBuilder.create()
            alert.setTitle("Delete Grave")
            alert.show()

        }

        binding.sendChip.setOnClickListener {

            val graveInfo = "${grave.firstName} ${grave.lastName} birth year - ${grave.birthDate}, death year - ${grave.deathDate}, marriage year - ${grave.marriageYear}, comment: ${grave.comment}"
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, graveInfo)
                type = "text/plain"
            }

            val chooser: Intent = Intent.createChooser(intent, "Choose an app")
            startActivity(chooser)
        }

        binding.editChip.setOnClickListener {
            val intent = Intent(this, CreateGraveActivity::class.java)
            intent.putExtra(CreateGraveActivity.GRAVE_ROW_ID, grave.id
            )
            intent.putExtra(CreateGraveActivity.EDIT_GRAVE, true)
            startActivity(intent)
        }
    }

    fun initViewModel(rowId: Int){
        val cemeteryDao = CemeteryRoomDatabase.getDatabase(application).cemDao()
        val repository = CemeteryRepository(cemeteryDao)
        val viewModelFacotory = GraveDetailViewModelFactory(application, repository, rowId)
        viewModel = ViewModelProvider(this, viewModelFacotory).get(GraveDetailViewModel::class.java)

    }

    companion object{
        val GRAVE_ROW_ID = "grave_row_id"
    }
}