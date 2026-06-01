package com.drake.droidblox.datastores.playsessions

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.drake.droidblox.datastores.Defaults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaySessionsManager @Inject constructor(
    val dataStore: DataStore<Preferences>
) : Defaults(dataStore) {
    companion object {
        val LOG_PLAY_SESSIONS           = booleanPreferencesKey("logPlaySessions")

        val RECENT_GAMES_PLAYED         = stringPreferencesKey("recentGamesPlayed")

        val CURRENT_PLACE_ID            = longPreferencesKey("currentPlaceId")
        val CURRENT_JOB_ID              = stringPreferencesKey("currentJobId")
        val CURRENT_TIME_OF_JOIN        = longPreferencesKey("currentTimeOfJoin")
    }

    private val json = Json { ignoreUnknownKeys = true }

    private fun stringToPlaySessions(string: String?): List<PlaySession> =
        json.decodeFromString(string ?: "[]")

//    suspend fun getPlaySessions(): List<PlaySession> =
//        stringToPlaySessions(dataStore.data.first()[RECENT_GAMES_PLAYED] ?: "{}")

    val playSessionsFlow: Flow<List<PlaySession>> = dataStore.data
        .map { preferences ->
            stringToPlaySessions(preferences[RECENT_GAMES_PLAYED])
        }

    suspend fun appendPlaySession(playSession: PlaySession) =
        dataStore.edit {
            it[RECENT_GAMES_PLAYED] = json.encodeToString(
                stringToPlaySessions(it[RECENT_GAMES_PLAYED])
                    .toMutableList()
                    .add(0, playSession)
            )
        }
}