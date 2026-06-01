package com.drake.droidblox.ui.view.views.playlogs.state

import com.drake.droidblox.ui.components.states.SwitchState
import com.drake.droidblox.ui.view.views.playlogs.models.PlaySessionComponent

data class PlayLogsScreenState(
    val logPlaySessions: Boolean = true,
    val playSessionsList: List<PlaySessionComponent> = emptyList()
)