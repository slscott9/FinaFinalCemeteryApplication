package com.example.finalcemeteryproject.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.finalcemeteryproject.R
import com.example.finalcemeteryproject.data.Cemetery
import com.example.finalcemeteryproject.data.CemeteryRepository
import com.example.finalcemeteryproject.data.CemeteryRoomDatabase
import com.example.finalcemeteryproject.databinding.ActivityCreateCemeteryBinding
import com.example.finalcemeteryproject.factories.CreateCemeteryViewModelFactory
import com.example.finalcemeteryproject.viewmodels.CreateCemeteryViewModel
import com.google.android.gms.location.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.util.*

class CreateCemeteryActivity : AppCompatActivity() {

    private lateinit var createCemeteryViewModel : CreateCemeteryViewModel
    private lateinit var binding: ActivityCreateCemeteryBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var  geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_cemetery)
        binding.lifecycleOwner = this
        initViewModel()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this) //Use its methods to get location updates
        locationRequest = LocationRequest()    //can also use this to get finer location
        geocoder = Geocoder(this, Locale.getDefault()) //gets lat and long into usable address objects


        createCemeteryViewModel.responseFailure.observe(this, androidx.lifecycle.Observer {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        binding.locationBtn.setOnClickListener {
            if (!isLocationEnabled()) {
                Toast.makeText(this, "Your location provider is turned off. Please turn it on.", Toast.LENGTH_SHORT).show()

                // This will redirect you to settings from where you need to turn on the location provider.
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            } else {
                // https://www.androdocs.com/kotlin/getting-current-location-latitude-longitude-in-android-using-kotlin.html
                Dexter.withActivity(this)
                    .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    .withListener(object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                            if (report!!.areAllPermissionsGranted()) {
                                startLocationUpdates()
                            }
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permissions: MutableList<PermissionRequest>?,
                            token: PermissionToken?
                        ) {
                            showRationalDialogForPermissions() //Tell user why they need permissions
                        }
                    }).onSameThread()
                    .check()
            }
        }

        binding.addCemButton.setOnClickListener {
            if (binding.nameEditText.text.isNullOrEmpty() ||
                binding.locationEditText.text.isNullOrEmpty() ||
                binding.stateEditText.text.isNullOrEmpty() ||
                binding.countyEditText.text.isNullOrEmpty() ||
                binding.townshipEditText.text.isNullOrEmpty() ||
                binding.rangeEditText.text.isNullOrEmpty() ||
                binding.sectionEditText.text.isNullOrEmpty() ||
                binding.firstYearEditText.text.isNullOrEmpty()
            ) {
                Toast.makeText(this, "Please entery all fields", Toast.LENGTH_SHORT).show()
            } else {

                val name = binding.nameEditText.text
                val location = binding.locationEditText.text
                val state = binding.stateEditText.text
                val county = binding.countyEditText.text
                val townShip = binding.townshipEditText.text
                val range = binding.rangeEditText.text
                val section = binding.sectionEditText.text
                val spot = binding.spotEditText.text
                val firstYear = binding.firstYearEditText.text
                val cemetery =
                    Cemetery(
                        cemeteryRowId = createCemeteryViewModel.newCemeteryKey ,
                        cemeteryName = name.toString(),
                        cemeteryLocation = location.toString(),
                        cemeteryState = state.toString(),
                        cemeteryCounty = county.toString(),
                        township = townShip.toString(),
                        range = range.toString(),
                        section = section.toString(),
                        spot = spot.toString(),
                        firstYear = firstYear.toString()
                    )
                Log.i("CreateCemeteryViewModel", "The cemeteryKey incremented is ${createCemeteryViewModel.newCemeteryKey}")

                createCemeteryViewModel.insertNewCemetery(cemetery)
                createCemeteryViewModel.sendNeCemeteryToNetwork(cemetery)
                finish()
            }
        }
    }

    private val locationCallback = object : LocationCallback() { //We say what happens when the fusedLocationClient.requestLocationUpdates returns location
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations){
                // Update UI with location data
                // ...
                val addressList: List<Address>? = geocoder.getFromLocation(location.latitude, location.longitude, 1) //converts latitude and longitude to an address
                if (addressList != null && addressList.isNotEmpty()) {
                    val address: Address = addressList?.get(0)
                    val sb = StringBuilder()
                    for (i in 0..address.maxAddressLineIndex) {
                        sb.append(address.getAddressLine(i)).append(",")
                    }
                    sb.deleteCharAt(sb.length - 1) //
                    binding.locationEditText.setText(sb) //set the text to the adress
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient?.requestLocationUpdates(locationRequest, //returns a Task object that longitude and lat can be extracted from
            locationCallback,                                           //send a callback that we define ourselves
            Looper.getMainLooper()) //started on a looper thread
    }


    private fun isLocationEnabled(): Boolean { //if false take user to location settings
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun showRationalDialogForPermissions() { //show to the user if their permission are not set
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()

    }

    private fun stopLocationUpdates(){
        fusedLocationClient.removeLocationUpdates(locationCallback) //stop requesting location
    }

    fun initViewModel(){
        val cemeteryDao = CemeteryRoomDatabase.getDatabase(application).cemDao()
        val repository = CemeteryRepository(cemeteryDao)
        val viewModelFactory = CreateCemeteryViewModelFactory(application, repository)
        createCemeteryViewModel = ViewModelProvider(this, viewModelFactory).get(CreateCemeteryViewModel::class.java)
    }
}