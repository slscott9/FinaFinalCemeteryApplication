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
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception

class CemeteryRepository(private val cemeteryDao: CemeteryDao) {


    val networkEntryPoint = ServiceBuilder.networkAccessor
    val cemeteryDatabaseList = cemeteryDao.getAllCemeteries()

    val networkCemeteryListResponse = MutableLiveData<Boolean>()
    val networkSendCemeteryResponse = MutableLiveData<Boolean>()




    //Called from ViewModel's init block, this ensures we have an up to date list of cemeteries when view model is created
    suspend fun refreshCemeteryList() {
        withContext(Dispatchers.IO) {
            try {
                val cemeteryNetworkList = networkEntryPoint.getCemeteriesFromNetwork()

                if(cemeteryNetworkList.isSuccessful){
                    cemeteryDao.insertCemeteryNetworkList(*cemeteryNetworkList.body()!!.asDatabaseModel())
                    withContext(Dispatchers.Main){
                        networkCemeteryListResponse.value = true
                    }
                }else{
                    withContext(Dispatchers.Main){
                        networkCemeteryListResponse.value = false
                    }
                }
            }catch (e: HttpException){
                Log.i("RefreshCems", e.message())

            }catch (eT: Throwable){
                Log.i("RefreshCems", "No network failure something else")
            }
        }
    }



    /*
        Problem is this method is called in CreateCemeteryActivity and finish is called.
        This suspend function is canceled when CreateCemeteryViewmodel is destroyed becauase the activity is destroyed

     */
    suspend fun sendCemeteryToNetwork(cemetery: List<Cemetery>){
        withContext(Dispatchers.IO){
            try {
                val sendCemeteryResponse = networkEntryPoint.sendNewCemeteryToNetwork(cemetery)
                Log.i("response code" , "${sendCemeteryResponse.code()}")
                if(sendCemeteryResponse.isSuccessful){
                    withContext(Dispatchers.Main){
                        networkSendCemeteryResponse.value = true
                    }
                }else{
                    withContext(Dispatchers.Main){
                        networkSendCemeteryResponse.value = false
                    }
                }
            }catch (e: HttpException){
                Log.i("SendCem", e.message())

            }catch (eT: Throwable){
                Log.i("Send Cem", "${eT.message}")
            }
        }
    }

    suspend fun getAllCemsForNetowrk() : List<Cemetery>{
        return withContext(Dispatchers.IO){
            cemeteryDao.getAllCemsForNetwork()
        }
    }



//called from CemeteryListViewModel this method is main safe, launch from a coroutine
//    suspend fun refreshCemeteryList(){
//       withContext(Dispatchers.IO){
//           val cemeteryNetworkList = networkEntryPoint.getCemeteriesFromNetworkNewWay()
//           cemeteryDao.insertCemeteryNetworkList(*cemeteryNetworkList.asDatabaseModel())
//       }
//    }


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

//
//     fun newWaySendCemeteryToNetwork(cemetery: Cemetery, onResult: (Cemetery?) -> Unit){ //need to send this as a json object to dad
//        networkEntryPoint.sendNewCemeteryToNetworkNewWay(
//            cemId = cemetery.cemeteryRowId.toString(),
//            cemName = cemetery.cemeteryName,
//            location = cemetery.cemeteryLocation,
//            county = cemetery.cemeteryCounty,
//            township = cemetery.township,
//            range = cemetery.range,
//            spot = cemetery.spot,
//            yearFounded = cemetery.firstYear,
//            state = cemetery.cemeteryState
//        ).enqueue(
//            object : retrofit2.Callback<Cemetery> {
//                override fun onFailure(call: Call<Cemetery>, t: Throwable) {
//
//                    onResult(null) //if onFailure called invoke onResult lambda passing null as its parameter
//                }
//                override fun onResponse(call: Call<Cemetery>, response: Response<Cemetery>) {
//
//                    val addedCemetery = response.body()
//
//                    onResult(addedCemetery) //if onResponse called assign the response body to addedCemtery and pass it to onResult lambda as parameter
//                }
//            }
//        )
//    }
//
//
//    fun sendGraveToNetwork(grave: Grave, onResult: (Grave?) -> Unit ){
//        networkEntryPoint.sendNewGraveToNetwork(
//            id = grave.graveRowId,
//            cemeteryId = grave.cemeteryId,
//            firstName = grave.firstName,
//            lastName = grave.lastName,
//            bornDate = grave.birthDate,
//            deathDate = grave.deathDate,
//            marriageYear = grave.marriageYear,
//            comment = grave.comment,
//            graveNum = grave.graveNumber
//        ).enqueue(
//            object : retrofit2.Callback<Grave> {
//                override fun onResponse(call: Call<Grave>, response: Response<Grave>) {
//                    val graveResponse = response.body()
//                    onResult(graveResponse)
//                }
//
//                override fun onFailure(call: Call<Grave>, t: Throwable) {
//                    onResult(null)
//                }
//            }
//        )
//    }










}
