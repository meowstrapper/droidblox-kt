package com.drake.droidblox.apiservice.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RobloxGameCreator(
    val name: String,
    @SerialName("hasVerifiedBadge")
    val isVerified: Boolean
)