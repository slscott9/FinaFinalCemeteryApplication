package com.example.finalcemeteryproject.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.finalcemeteryproject.data.CemeteryRepository
import com.example.finalcemeteryproject.data.CemeteryRoomDatabase
import com.example.finalcemeteryproject.network.ServiceBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber


/*
    Worker defines what the work does
 */
class RefreshDataWorker(appContext: Context,  params: WorkerParameters): CoroutineWorker(appContext, params) {


    companion object {
        const val WORK_NAME = "refresh_data_worker"
    }

    override suspend fun doWork(): Result {

        val appContext = applicationContext

        val cemeteryDatabase = CemeteryRoomDatabase.getDatabase(appContext).cemDao()
        val repository = CemeteryRepository(cemeteryDatabase)

        val cemeteryList = repository.getAllCemsForNetowrk()

        try{
           repository.refreshCemeteryList()
            repository.sendCemeteryToNetwork(cemeteryList)
            Timber.d("Work request for sync is run")

        }catch (e: HttpException){
            return Result.retry() //Work is enqueued again enqueue -> running -> success
        }
        return Result.success()

    }




}