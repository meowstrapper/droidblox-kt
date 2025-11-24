package com.drake.droidblox.backend.apis

import android.util.Log
import com.drake.droidblox.backend.apis.models.RobloxGame
import com.drake.droidblox.backend.apis.models.RobloxThumbnail
import com.drake.droidblox.backend.apis.models.RobloxUser
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlin.collections.mapNotNull

private const val TAG = "DBRobloxApi"

object RobloxApi {
    suspend fun fetchGameInfo(
        httpClient: HttpClient,
        universeIds: List<Long>
    ): List<RobloxGame>? {
        val requestTo =
            "https://games.roblox.com/v1/games?universeIds=${universeIds.joinToString(",")}"
        try {
            Log.d(TAG, "Requesting GET to $requestTo")
            val gamesInfoReq: HttpResponse = httpClient.get(requestTo)

            if (gamesInfoReq.status.value != 200) {
                Log.e(
                    TAG,
                    "Failed to get roblox game info. Got ${gamesInfoReq.status.value}\nText:\n${gamesInfoReq.bodyAsText()}"
                )
            } else {
                val deserializedGamesInfo: List<RobloxGame> = Json.decodeFromString(
                    Json.encodeToString(gamesInfoReq.body<Map<String, Any>>()["data"])
                ) // any way how to do this efficiently?
                return universeIds.flatMap { universeId ->
                    deserializedGamesInfo.filter { game -> game.universeId == universeId }
                } // roblox fucks up the returned data if there are duplicates of universe ids
            }
        } catch (e: Exception) {
            Log.e(TAG, "Something went wrong while fetching game info!; ${e.message}")
        }
        return null
    }

    suspend fun fetchUserInfo(
        httpClient: HttpClient,
        userId: Long
    ): RobloxUser? {
        val requestTo = "https://users.roblox.com/v1/users/$userId"
        try {
            Log.d(TAG, "Requesting GET to $requestTo")
            val usernameReq: HttpResponse = httpClient.get(requestTo)

            if (usernameReq.status.value != 200) {
                Log.e(
                    TAG,
                    "Failed to fetch roblox user info. Got ${usernameReq.status.value}\nText:\n${usernameReq.bodyAsText()}"
                )
            } else {
                return usernameReq.body<RobloxUser>()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Something went wrong while fetching user info!; ${e.message}")
        }
        return null

    }


    suspend fun fetchThumbnails(
        httpClient: HttpClient,
        thumbnails: List<RobloxThumbnail>
    ): List<String>? {
        val requestTo = "https://thumbnails.roblox.com/v1/batch"

        try {
            Log.d(TAG, "Requesting POST to $requestTo with data $thumbnails")
            val thumbnailsReq: HttpResponse = httpClient.post(requestTo) {
                contentType(ContentType.Application.Json)
                setBody(thumbnails)
            }
            if (thumbnailsReq.status.value != 200) {
                Log.e(
                    TAG,
                    "Failed to get roblox thumbnail(s). Got ${thumbnailsReq.status.value}\nText:\n${thumbnailsReq.bodyAsText()}"
                )
            } else {
                return (thumbnailsReq.body<Map<String, List<Map<String, Any>>>>()["data"]
                    ?: emptyList()).mapNotNull {
                    it["imageUrl"] as? String
                } // 🙏🙏🙏 (TODO: Optimize this)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Something went wrong while fetching roblox thumbnails!; ${e.message}")
        }
        return null
    }
}
