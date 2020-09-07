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

//    @GET("/cgi-bin/getCems.pl")
//    fun getCemeteriesFromNetwork(): Deferred<NetworkCemeteryContainer>


    @GET("/cgi-bin/getCems.pl")
     suspend fun getCemeteriesFromNetworkNewWay(): retrofit2.Response<NetworkCemeteryContainer>

    /*
        So the magic now is that you can create suspend methods in your Retrofit interface and directly return your data object.
     */

//    @GET("/cgi-bin/getCems.pl")
//     fun getCemeteriesFromNetworkNewWay(): Call<NetworkCemeteryContainer>     //new way mark as suspend get your container object no more deferred
//    //Use Response<NetworkCemeteryContainer> in order to get a response back and use it to display information about request




    @FormUrlEncoded
    @GET("/cgi-bin/addCem.pl")                  //old way
    fun sendNewCemeteryToNetwork(
        @Field("cem_id") cemId: String,
        @Field("name") cemName: String,
        @Field("loc") location: String,
        @Field("state") state: String,
        @Field("county") county: String,
        @Field("twnsp") township: String,
        @Field("range") range: String,
        @Field("spot") spot: String,
        @Field("fyear") yearFounded: String,
        @Field("section") section: String
    ): Cemetery //Use Call .enqueue onResponse onFailure or Response to get metadata back about the get request


@POST("/cgi-bin/addCem.pl")
@FormUrlEncoded // new way mark as suspend return your Cemetery
     fun sendNewCemeteryToNetworkNewWay(
    @Field("cem_id") cemId: String,
    @Field("name") cemName: String,
    @Field("loc") location: String,
    @Field("state") state: String,
    @Field("county") county: String,
    @Field("twnsp") township: String,
    @Field("range") range: String,
    @Field("spot") spot: String,
    @Field("fyear") yearFounded: String
): Call<Cemetery> //Call we implement callback with onFailure and onSucess




    @FormUrlEncoded
    @POST("/cgi-bin/addGrave.pl")
    fun sendNewGraveToNetwork(
        @Field("id") id: Int,
        @Field("cemetery_id") cemeteryId: Int,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("born_data") bornDate: String,
        @Field("death_data") deathDate: String,
        @Field("married") marriageYear: String,
        @Field("comment") comment: String,
        @Field("grave_number") graveNum: String
    ): Call<Grave>
}

private val moshi = Moshi.Builder() //can use later
    .add(KotlinJsonAdapterFactory())
    .build()

object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://dts.scott.net")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
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

