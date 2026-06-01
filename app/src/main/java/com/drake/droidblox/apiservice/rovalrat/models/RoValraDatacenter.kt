package com.drake.droidblox.apiservice.rovalrat.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoValraDatacenter(
    @SerialName("location_id")
    val locationId: Int,
    val dataCenterIds: List<Int>,
    val location: RoValraDatacenterLocation,
    val inactive: Boolean,
    val loadbalancing: Boolean
)