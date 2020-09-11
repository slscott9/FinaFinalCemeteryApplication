package com.example.finalcemeteryproject.network

import com.example.finalcemeteryproject.data.Cemetery
import com.example.finalcemeteryproject.data.Grave
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface NetworkApi {
    @GET("/cgi-bin/getCems.pl")
    suspend fun getCemeteriesFromNetwork(): retrofit2.Response<NetworkCemeteryContainer>
//
//    @FormUrlEncoded
//    @POST("/cgi-bin/addCem.pl")
//    suspend fun sendNewCemeteryToNetwork(
//        @Field("cem_id") cemId: String,
//        @Field("name") cemName: String,
//        @Field("loc") location: String,
//        @Field("state") state: String,
//        @Field("county") county: String,
//        @Field("twnsp") township: String,
//        @Field("range") range: String,
//        @Field("spot") spot: String,
//        @Field("fyear") yearFounded: String,
//
//    ): retrofit2.Response<Cemetery>

    @FormUrlEncoded
    @POST("/cgi-bin/addCem.pl")
    suspend fun sendNewCemeteryToNetwork(@Body cemeteryList: List<Cemetery>): retrofit2.Response<Cemetery>
}

private val moshi = Moshi.Builder() //can use later
    .add(KotlinJsonAdapterFactory())
    .build()

object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://dts.scott.net")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
//        .addCallAdapterFactory(CoroutineCallAdapterFactory())

        .client(client)
        .build()

    val networkAccessor = retrofit.create(
        NetworkApi::class.java)

    //MoshiConverterFactory.create(moshi) if we are to use moshi converter this needs
    //to be in .addConverterFactory

//    fun<T> buildService(service: Class<T>): T{
//        return retrofit.create(service)
//    }

}

