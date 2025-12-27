package com.drake.droidblox.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.drake.droidblox.logger.AndroidLogger
import com.drake.droidblox.sharedprefs.models.PlaySession
import kotlinx.serialization.json.Json

class PlaySessionsManager(
    context: Context
) {
    companion object {
        private const val TAG = "DBPlaySessionsManager"
        private val logger = AndroidLogger
    }
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("playsessions", Context.MODE_PRIVATE)

    var recentGamesPlayed: List<PlaySession>
        get() {
            val jsonString = sharedPreferences.getString("recentGamesPlayed", null)
            var toReturn: List<PlaySession> = emptyList()
            if (jsonString != null) {
                try {
                    toReturn = Json.decodeFromString<List<PlaySession>>(jsonString)
                } catch (e: Exception) {
                    logger.e(TAG, "Something went wrong while getting a list of games played!; ${e.message}")
                    toReturn = emptyList()
                }
            }
            logger.d(TAG, "get = $toReturn")
            return toReturn
        }
        set(value) {
            val jsonString = Json.encodeToString(value)
            logger.d(TAG, "set = $jsonString")
            sharedPreferences.edit { putString("recentGamesPlayed", jsonString) }
        }

    fun addPlaySession(playSession: PlaySession) {
        logger.d(TAG, "Adding play session: $playSession")
        val currentGames = recentGamesPlayed.toMutableList()
        currentGames.add(0, playSession)
        recentGamesPlayed = currentGames.toList()
    }
}