package com.drake.droidblox.apiservice

import com.drake.droidblox.apiservice.models.RobloxGame
import com.drake.droidblox.apiservice.models.RobloxThumbnail
import com.drake.droidblox.apiservice.models.RobloxUser
import com.drake.droidblox.logger.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import kotlinx.serialization.json.Json
import javax.inject.Singleton
import kotlin.collections.mapNotNull

@Singleton
class RobloxApi @Inject constructor(
    private val logger: Logger,
    private val httpClient: HttpClient
) {
    companion object {
        private const val TAG = "DBRobloxApi"
    }

    suspend fun fetchGameInfo(
        universeIds: List<Long>
    ): List<RobloxGame>? {
        val requestTo =
            "https://games.roblox.com/v1/games?universeIds=${universeIds.joinToString(",")}"
        try {
            logger.d(TAG, "Requesting GET to $requestTo")
            val gamesInfoReq: HttpResponse = httpClient.get(requestTo)

            if (gamesInfoReq.status.value != 200) {
                logger.e(
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
            logger.e(TAG, "Something went wrong while fetching game info!; ${e.message}")
        }
        return null
    }

    suspend fun fetchUserInfo(
        userId: Long
    ): RobloxUser? {
        val requestTo = "https://users.roblox.com/v1/users/$userId"
        try {
            logger.d(TAG, "Requesting GET to $requestTo")
            val usernameReq: HttpResponse = httpClient.get(requestTo)

            if (usernameReq.status.value != 200) {
                logger.e(
                    TAG,
                    "Failed to fetch roblox user info. Got ${usernameReq.status.value}\nText:\n${usernameReq.bodyAsText()}"
                )
            } else {
                return usernameReq.body<RobloxUser>()
            }
        } catch (e: Exception) {
            logger.e(TAG, "Something went wrong while fetching user info!; ${e.message}")
        }
        return null

    }


    suspend fun fetchThumbnails(
        thumbnails: List<RobloxThumbnail>
    ): List<String>? {
        val requestTo = "https://thumbnails.roblox.com/v1/batch"

        try {
            logger.d(TAG, "Requesting POST to $requestTo with data $thumbnails")
            val thumbnailsReq: HttpResponse = httpClient.post(requestTo) {
                contentType(ContentType.Application.Json)
                setBody(thumbnails)
            }
            if (thumbnailsReq.status.value != 200) {
                logger.e(
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
            logger.e(TAG, "Something went wrong while fetching roblox thumbnails!; ${e.message}")
        }
        return null
    }
}
