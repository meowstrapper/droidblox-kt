package com.drake.droidblox.apiservice.iplocation.models

import kotlinx.serialization.Serializable

@Serializable
data class IpLocation(
    val city: String,
    val region: String,
    val country: String,
    val loc: String
)