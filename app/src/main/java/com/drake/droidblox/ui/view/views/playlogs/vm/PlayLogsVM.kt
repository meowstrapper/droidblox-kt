package com.drake.droidblox.ui.view.views.playlogs.vm

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drake.droidblox.apiservice.roblox.RobloxApi
import com.drake.droidblox.apiservice.roblox.models.RobloxThumbnail
import com.drake.droidblox.datastores.SettingsManager
import com.drake.droidblox.datastores.playsessions.PlaySession
import com.drake.droidblox.datastores.playsessions.PlaySessionsManager
import com.drake.droidblox.ui.view.views.playlogs.models.PlaySessionComponent
import com.drake.droidblox.ui.view.views.playlogs.state.PlayLogsScreenState
import com.drake.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayLogsVM @Inject constructor(
    @ApplicationContext val context: Context,
    val logger: Logger,
    val playSessionsManager: PlaySessionsManager,
    val settingsManager: SettingsManager,
    val robloxApi: RobloxApi
): ViewModel() {
    companion object {
        private const val TAG = "PlayLogsVM"
    }

    val uiState: StateFlow<PlayLogsScreenState> = playSessionsManager.dataStore.data.map { prefs ->
        PlayLogsScreenState(
            logPlaySessions = prefs[PlaySessionsManager.LOG_PLAY_SESSIONS] ?: true,
            //playSessionsList = playSessionComponents
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = PlayLogsScreenState()
    )

//    private val _playSessionComponents = MutableStateFlow<List<PlaySessionComponent>>(emptyList())
//    val playSessionComponents: StateFlow<List<PlaySessionComponent>> = _playSessionComponents.asStateFlow()

    fun <T> setSetting(key: Preferences.Key<T>, value: T) = viewModelScope.launch(Dispatchers.IO) {
        playSessionsManager.set(key, value)
    }

//    suspend fun processPlaySessions(): List<PlaySessionComponent>? {
//        logger.i(TAG, "Loading play sessions")
//
//        val recentGamesPlayed: List<PlaySession> = playSessionsManager.playSessionsFlow.first()
//
//        val thumbnailUrls = robloxApi.fetchThumbnailUrl(
//            recentGamesPlayed
//                .map { it.universeId }
//                .distinct()
//                .map { RobloxThumbnail(
//                    targetId = it,
//                    type = "GameIcon",
//                    size = "256x256",
//                    isCircular = false
//                ) }
//        )
//        if (thumbnailUrls == null) {
//            logger.e(TAG, "Something went wrong while trying to fetch thumbnail urls! Stopping action")
//            return null
//        }
//
//        val gameInfo = robloxApi.fetchGameInfo(
//            recentGamesPlayed
//                .map { it.universeId }
//                .distinct()
//        )
//        if (gameInfo == null) {
//            logger.e(TAG, "Something went wrong while trying to fetch game infos! Stopping action")
//            return null
//        }
//
//        return recentGamesPlayed.map { playSession ->
//            PlaySessionComponent(
//                placeId = playSession.placeId,
//                universeId = playSession.universeId,
//                jobId = playSession.jobId,
//                gameName = gameInfo[playSession.universeId]!!.name,
//                creator = gameInfo[playSession.universeId]!!.creator.name,
//                iconUrl = thumbnailUrls[playSession.universeId]!!,
//                playedAt = playSession.playedAt,
//                leftAt = playSession.leftAt
//            )
//        }.also {
//            logger.d(TAG, "Done loading play sessions")
//        }
//
//    }

    fun launchRoblox(placeId: Long, jobId: String) =
        com.drake.droidblox.roblox.launchRoblox(
            context = context,
            placeId = placeId,
            jobId = jobId
        )

//    private val _playSessions = MutableStateFlow<List<PlaySessionComponent>>(emptyList())
//    val playSessions: StateFlow<List<PlaySessionComponent>> = _playSessions.asStateFlow()
//
//    init {
//        viewModelScope.launch(Dispatchers.IO)  {
//            loadPlaySessions()
//        }
//    }
//
//    suspend fun loadPlaySessions() {
//        logger.d(TAG, "Loading play sessions")
//        val recentGamesPlayed = playSessionsManager.recentGamesPlayed
//
//        val thumbnailUrls = robloxApi.fetchThumbnailUrl(
//            recentGamesPlayed
//                .map { it.universeId }
//                .distinct()
//                .map { RobloxThumbnail(
//                    targetId = it,
//                    type = "GameIcon",
//                    size = "256x256",
//                    isCircular = false
//                ) })
//        if (thumbnailUrls == null) {
//            logger.e(TAG, "Something went wrong while trying to fetch thumbnail urls! Stopping action")
//            return
//        }
//
//        val gameInfo: Map<Long, RobloxGame>? = robloxApi.fetchGameInfo(
//            recentGamesPlayed
//                .map { it.universeId }
//                .distinct())
//        if (gameInfo == null) {
//            logger.e(TAG, "Something went wrong while trying to fetch game infos! Stopping action")
//            return
//        }
//
//        logger.d(TAG, "Setting play sessions value")
//        _playSessions.value = recentGamesPlayed.map {
//            PlaySessionComponent(
//                universeId = it.universeId,
//                gameName = gameInfo[it.universeId]!!.name,
//                creator = gameInfo[it.universeId]!!.creator.name,
//                iconUrl = thumbnailUrls[it.universeId]!!,
//                playedAt = it.playedAt,
//                leftAt = it.leftAt,
//                deeplink = it.deeplink
//            )
//        }
//    }
//
//    fun launchRoblox(deeplink: String) {
//        com.drake.droidblox.roblox.launchRoblox(context, deeplink, logger)
//    }
}