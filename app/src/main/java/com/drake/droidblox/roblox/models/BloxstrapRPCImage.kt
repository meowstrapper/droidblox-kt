package com.drake.droidblox.roblox.models

import kotlinx.serialization.Serializable

@Serializable
data class BloxstrapRPCImage(
    val assetId: Long?,
    val hoverText: String?,
    val clear: Boolean?,
    val reset: Boolean?
)
