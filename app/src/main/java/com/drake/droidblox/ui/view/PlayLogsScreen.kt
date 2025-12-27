package com.drake.droidblox.ui.view

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.drake.droidblox.DBApplication
import com.drake.droidblox.apiservice.RobloxApi
import com.drake.droidblox.apiservice.models.RobloxThumbnail
import com.drake.droidblox.sharedprefs.PlaySessionsManager
import com.drake.droidblox.ui.components.BasicScreen
import com.drake.droidblox.ui.components.RecentGamePlayed
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val TAG = "DBPlayLogs"

//@Composable
//fun PlayLogsScreen(
//    navController: NavController? = null
//) {
//    val httpClient = HttpClient {
//        install(ContentNegotiation) {
//            json(Json {
//                ignoreUnknownKeys = true
//                isLenient = true
//            })
//        }
//    }
//    val currentPlayLogs = playSessionsManager.recentGamesPlayed
//    var loadingPlayLogs by remember { mutableStateOf(true) }
//    val playSessions = remember { mutableStateOf<List<PlaySession>>(emptyList()) }
//    LaunchedEffect(Unit) {
//        Log.d(TAG, "Loading Play Logs; $currentPlayLogs")
//
//        val universeIdsToRequest = currentPlayLogs.map { it.universeId }
//        Log.d(TAG, "Requesting the following universe ids; $universeIdsToRequest")
//        val gameInfosReq = RobloxApi.fetchGameInfo(httpClient, universeIdsToRequest)
//        if (gameInfosReq == null) {
//            Log.e(TAG, "Something went wrong with Roblox's Game API! Not doing anything")
//            return@LaunchedEffect
//        }
//        Log.d(TAG, "Requested game infos; $gameInfosReq")
//
//        val thumbnailsToReq = currentPlayLogs.map { RobloxThumbnail(
//            it.universeId,
//            "GameIcon",
//            "512x512",
//            false
//        ) }
//        Log.d(TAG, "Requesting the following thumbnails; $thumbnailsToReq")
//        val thumbnailsReq = RobloxApi.fetchThumbnails(httpClient, thumbnailsToReq)
//        if (thumbnailsReq == null) {
//            Log.e(TAG, "Something went wrong with Roblox's Thumbnail API! Not doing anything")
//            return@LaunchedEffect
//        }
//        Log.d(TAG, "Requested thumbnails: $thumbnailsReq")
//
//        Log.d(TAG, "Loading play logs to screen")
//        playSessions.value = currentPlayLogs.mapIndexed { index, playSession ->
//            val gameInfo = gameInfosReq[index]
//            val thumbnailUrl = thumbnailsReq[index]
//
//            PlaySession(
//                playSession.playedAt, gameInfo.name,
//                gameInfo.creator.name, thumbnailUrl,
//                playSession.playedAt, playSession.leftAt,
//                playSession.deeplink
//            )
//        }
//        Log.d(TAG, "Done loading")
//        loadingPlayLogs = false
//    }
//    BasicScreen("Play Logs", navController, true, lazyColumnContents = {
////        repeat(20) {
////            RecentGamePlayed(
////                "RoValra",
////                "Valra",
////                "https://avatars.githubusercontent.com/u/124619531?v=4",
////                System.currentTimeMillis() - 10000,
////                System.currentTimeMillis(),
////                "roblox://experiences/start?placeId=142823291"
////            )
////        }
//        if (loadingPlayLogs) {
//            item {
//                Box(
//                    modifier = Modifier.fillParentMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//            }
//        } else {
//            items(playSessions.value, { it.id }) {
//                RecentGamePlayed(
//                    it.gameName, it.creator,
//                    it.iconUrl, it.playedAt,
//                    it.leftAt, it.deeplink
//                )
//            }
//        }
//    })
//}
