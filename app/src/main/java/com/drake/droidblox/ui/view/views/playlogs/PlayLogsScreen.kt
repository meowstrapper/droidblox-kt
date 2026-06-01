package com.drake.droidblox.ui.view.views.playlogs

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.drake.droidblox.datastores.playsessions.PlaySessionsManager
import com.drake.droidblox.ui.components.BasicScreen
import com.drake.droidblox.ui.components.ExtendedSwitch
import com.drake.droidblox.ui.view.views.playlogs.models.RecentGamePlayed
import com.drake.droidblox.ui.components.SectionText
import com.drake.droidblox.ui.view.views.playlogs.state.PlayLogsScreenCallbacks
import com.drake.droidblox.ui.view.views.playlogs.vm.PlayLogsVM
import com.drake.droidblox.ui.view.views.playlogs.state.PlayLogsScreenState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("ConstantLocale")
private val dateFormat = SimpleDateFormat("HH:mm MM/dd/yyyy", Locale.getDefault())

@Composable
fun PlayLogsScreen(
    navController: NavController? = null,
    state: PlayLogsScreenState = PlayLogsScreenState(),
    callback: PlayLogsScreenCallbacks = PlayLogsScreenCallbacks()
) {
    BasicScreen("Play Logs", navController, useLazyColumn = true, lazyColumnContents = {
        item {
            ExtendedSwitch(
                title = "Log play sessions",
                subtitle = "Whenever you play a game, DroidBlox will log that here, including the exact server you played at. (Useful if you unexpectedly disconnected)",
                enabled = state.logPlaySessions,
                onClick = callback.logPlaySessions
            )
            SectionText("Play Sessions")
        }
        items(
            items = state.playSessionsList,
            key = { it.playedAt }
        ) {
            val playedAtDate = remember {
                dateFormat.format(Date(it.playedAt))
            }
            val leftAtDate = remember {
                dateFormat.format(Date(it.leftAt))
            }

            RecentGamePlayed(
                gameName = it.gameName,
                gameCreator = it.creator,
                gameIconUrl = it.iconUrl,
                playedAt = playedAtDate,
                leftAt = leftAtDate,
                onRejoinClick = { callback.rejoin(it.placeId, it.jobId) }
            )
        }
    })
}

@Composable
fun PlayLogsScreenImpl(
    navController: NavController
) {
    val viewModel: PlayLogsVM = hiltViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PlayLogsScreen(
        navController = navController,
        state = uiState,
        callback = PlayLogsScreenCallbacks(
            logPlaySessions = {
                viewModel.setSetting(
                    key = PlaySessionsManager.LOG_PLAY_SESSIONS,
                    value = it
                )
            },
            rejoin = { placeId, jobId ->
                viewModel.launchRoblox(
                    placeId = placeId,
                    jobId = jobId
                )
            }
        )
    )
}