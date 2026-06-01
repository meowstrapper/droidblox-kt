package com.drake.droidblox.apiservice.discord.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscordExternalAssetMP(
    @SerialName("external_asset_path")
    val externalAssetPath: String
)