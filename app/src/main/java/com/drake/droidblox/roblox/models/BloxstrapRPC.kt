package com.drake.droidblox.roblox.models

import kotlinx.serialization.Serializable

@Serializable
data class BloxstrapRPC(
    val details: String? = null,
    val state: String? = null,
    val timeStart: Long?  = null,
    val timeEnd: Long?  = null,
    val smallImage: BloxstrapRPCImage?  = null,
    val largeImage: BloxstrapRPCImage?  = null
)