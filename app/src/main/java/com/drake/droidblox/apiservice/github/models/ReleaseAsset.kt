package com.drake.droidblox.apiservice.github.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReleaseAsset(
    val id: Long,
    val name: String,
    val size: Long,
    @SerialName("browser_download_url")
    val browserDownloadUrl: String
)