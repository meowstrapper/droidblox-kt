package com.drake.droidblox.apiservice.roblox.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RobloxGame(
    val name: String,
    val creator: RobloxGameCreator,
    @SerialName("rootPlaceId")
    val placeId: Long,
    @SerialName("id")
    val universeId: Long
)

@Serializable
data class RawRobloxGame(
    val data: List<RobloxGame>
)