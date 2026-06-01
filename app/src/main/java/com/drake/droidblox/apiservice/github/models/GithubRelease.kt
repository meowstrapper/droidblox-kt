package com.drake.droidblox.apiservice.github.models

import kotlinx.serialization.Serializable

@Serializable
data class GithubRelease(
    val id: Long,
    val name: String,
    val body: String,
    val assets: List<ReleaseAsset>
)