package com.drake.droidblox.datastores.playsessions

import kotlinx.serialization.Serializable

@Serializable
data class PlaySession(
    val universeId: Long,
    val placeId: Long,
    val jobId: String,
    val playedAt: Long,
    val leftAt: Long
)