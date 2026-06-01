package com.drake.droidblox.apiservice.discord.models

import kotlinx.serialization.Serializable

@Serializable
data class DiscordUserMe(
    val username: String
)
