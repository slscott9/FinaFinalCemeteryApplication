package com.example.finalcemeteryproject.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalcemeteryproject.network.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception

class CemeteryRepository(private val cemeteryDao: CemeteryDao) {

    val networkCemeteryListFailure =  MutableLiveData<Boolean>(false)


    val retrofit = ServiceBuilder.networkAccessor
    val cemeteryDatabaseList = cemeteryDao.getAllCemeteries()



    fun refreshCemeteryList(){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val networkCemeteryList = retrofit.getCemeteriesFromNetworkNewWay()
                if(networkCemeteryList.isSuccessful){
                    insertNetworkCems(networkCemeteryList.body()!!)
                }else{
                    networkCemeteryListFailure.value = true
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    networkCemeteryListFailure.value = true

                }
            }
        }
    }



    suspend fun insertNetworkCems(cemetery: NetworkCemeteryContainer){
        cemeteryDao.insertCemeteryNetworkList(*cemetery.asDatabaseModel())
    }


    fun getAllCemeteries() : LiveData<List<Cemetery>> {
        return cemeteryDao.getAllCemeteries()
    }


    suspend fun insertCemetery(cemetery: Cemetery){
        cemeteryDao.insertCemetery(cemetery)
    }

    suspend fun deleteGrave(rowid: Int){
        cemeteryDao.deleteGrave(rowid)
    }

    suspend fun insertGrave(grave: Grave){
        cemeteryDao.insertGrave(grave)
    }

    fun getGraveWithRowId(rowid: Int): LiveData<Grave>{
        return cemeteryDao.getGraveWithRowid(rowid)
    }

    fun getCemeteryWithId(cemeteryId: Int): LiveData<Cemetery>{
        return cemeteryDao.getCemeteryWithId(cemeteryId)
    }

    fun getGravesWithCemeteryId(cemeteryId: Int): LiveData<List<Grave>>{
        return cemeteryDao.getGravesWithCemeteryId(cemeteryId)
    }

     suspend fun getMaxCemeteryRowNum() : Int? {
       return  cemeteryDao.getMaxCemeteryRowNum()
    }


     fun newWaySendCemeteryToNetwork(cemetery: Cemetery, onResult: (Cemetery?) -> Unit){ //need to send this as a json object to dad
        retrofit.sendNewCemeteryToNetworkNewWay(
            cemId = cemetery.cemeteryRowId.toString(),
            cemName = cemetery.cemeteryName,
            location = cemetery.cemeteryLocation,
            county = cemetery.cemeteryCounty,
            township = cemetery.township,
            range = cemetery.range,
            spot = cemetery.spot,
            yearFounded = cemetery.firstYear,
            state = cemetery.cemeteryState
        ).enqueue(
            object : retrofit2.Callback<Cemetery> {
                override fun onFailure(call: Call<Cemetery>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<Cemetery>, response: Response<Cemetery>) {
                    val addedCemetery = response.body()
                    onResult(addedCemetery)
                }
            }
        )
    }










}
