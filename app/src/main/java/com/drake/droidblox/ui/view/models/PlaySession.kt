package com.drake.droidblox.ui.view.models

private data class PlaySession(
    val id: Long,
    val gameName: String,
    val creator: String,
    val iconUrl: String,
    val playedAt: Long,
    val leftAt: Long,
    val deeplink: String
)