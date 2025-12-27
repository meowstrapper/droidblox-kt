package com.drake.droidblox.apiservice.models

import kotlinx.serialization.Serializable

@Serializable
data class RobloxThumbnail(
    val targetId: Long,
    val type: String,
    val size: String,
    val isCircular: Boolean
)
