package com.drake.droidblox.apiservice.rovalrat.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoValraDatacenterLocation(
    val city: String,
    val region: String,
    val country: String,
    @SerialName("country_name")
    val countryName: String,
    val latLong: List<String>
)