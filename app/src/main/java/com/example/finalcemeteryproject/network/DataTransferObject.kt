package com.example.finalcemeteryproject.network

import com.example.finalcemeteryproject.data.Cemetery
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkCemeteryContainer(val records: List<NetworkCemetery>)

@JsonClass(generateAdapter = true)
data class NetworkCemetery(
    val cemetery_id: String,
    val name: String,
    val location: String,
    val state: String,
    val county: String,
    val T: String,
    val R: String,
    val spot: String,
    val first_year: String,
    val S: String
)


//Convert our Network objects into database models
fun NetworkCemeteryContainer.asDatabaseModel(): Array<Cemetery> {
    return records.map {
        Cemetery(
            cemeteryRowId = it.cemetery_id.toInt(),
            cemeteryName = it.name,
            cemeteryLocation = it.location,
            cemeteryState = it.state,
            cemeteryCounty = it.county,
            township = it.T,
            range = it.R,
            spot = it.spot,
            firstYear = it.first_year,
            section = it.S
        )
    }.toTypedArray()
}

//@JsonClass(generateAdapter = true)
//data class NetworkGraveContainer(val graveNetworkList: List<NetworkGrave>)
//
//@JsonClass(generateAdapter = true)
//data class NetworkGrave(
//
//)
