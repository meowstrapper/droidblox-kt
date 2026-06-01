package com.drake.droidblox.ui.view.views.playlogs.models

data class PlaySessionComponent(
    val placeId: Long,
    val universeId: Long,
    val jobId: String,
    val gameName: String,
    val creator: String,
    val iconUrl: String,
    val playedAt: Long,
    val leftAt: Long
)