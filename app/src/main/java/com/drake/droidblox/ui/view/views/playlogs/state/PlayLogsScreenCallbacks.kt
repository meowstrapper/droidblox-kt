package com.drake.droidblox.ui.view.views.playlogs.state

data class PlayLogsScreenCallbacks(
    val logPlaySessions: (Boolean) -> Unit = {},
    val rejoin: (placeId: Long, jobId: String) -> Unit = { placeId, jobId -> },
)