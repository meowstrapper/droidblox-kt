package com.drake.droidblox.backend.apis.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RobloxGameCreator(
    val name: String,
    @SerialName("hasVerifiedBadge")
    val isVerified: Boolean
)