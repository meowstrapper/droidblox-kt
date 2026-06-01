package com.drake.droidblox.apiservice.rovalrat.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoValraServer(
    val city: String? = null,
    val country: String,
    @SerialName("datacenter_id")
    val datacenterId: Int,
    @SerialName("first_seen")
    val firstSeen: String,
    @SerialName("ip_address")
    val ipAddress: String,
    @SerialName("place_version")
    val region: String? = null,
    @SerialName("region_code")
    val regionCode: String,
    @SerialName("server_id")
    val serverId: String
)

@Serializable
data class RawRoValraServers(
    val servers: List<RoValraServer>
)