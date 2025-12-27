package com.drake.droidblox.apiservice.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RobloxUser(
    val name: String,
    val displayName: String,
    @SerialName("hasVerifiedBadge")
    val isVerfied: Boolean
)
