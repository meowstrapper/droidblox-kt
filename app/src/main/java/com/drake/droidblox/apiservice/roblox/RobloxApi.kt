package com.drake.droidblox.apiservice.roblox

import com.drake.droidblox.apiservice.roblox.models.RawRobloxGame
import com.drake.droidblox.apiservice.roblox.models.RawRobloxThumbnailResponse
import com.drake.droidblox.apiservice.roblox.models.RobloxGame
import com.drake.droidblox.apiservice.roblox.models.RobloxThumbnail
import com.drake.droidblox.apiservice.roblox.models.RobloxUser
import com.drake.logger.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RobloxApi @Inject constructor(
    private val logger: Logger,
    private val httpClient: HttpClient
) {
    companion object {
        private const val TAG = "RobloxApi"
    }

    suspend fun fetchGameInfo(
        universeIds: List<Long>
    ): Map<Long, RobloxGame>? {
        logger.d(TAG, "Fetching game info for the following universe id(s): $universeIds")
        val gamesInfoReq: HttpResponse = httpClient.get(
            "https://games.roblox.com/v1/games?universeIds=${universeIds.joinToString(",")}"
        )

        if (gamesInfoReq.status != HttpStatusCode.Companion.OK) {
            logger.e(TAG, "Couldn't fetch game info for the following universe id(s): $universeIds")
            return null
        } else {
            val gamesInfo: RawRobloxGame = gamesInfoReq.body()
            val mapOfGameInfos: Map<Long, RobloxGame> = gamesInfo.data.associateBy { it.universeId }
//            val listOfRobloxGame: List<RobloxGame> = universeIds.flatMap { universeId ->
//                gamesInfo.data.filter { game -> game.universeId == universeId }
//            } // roblox fucks up the returned data if there are duplicates of universe ids
            logger.d(TAG, "universe id(s): $universeIds data: $mapOfGameInfos")
            return mapOfGameInfos
        }

    }

    suspend fun fetchGameInfo(universeId: Long) = fetchGameInfo(listOf(universeId))?.values?.first()

    suspend fun fetchUserInfo(
        userId: Long
    ): RobloxUser? {
        logger.d(TAG, "fetching user info for user id: $userId")
        val usernameReq: HttpResponse = httpClient.get(
            "https://users.roblox.com/v1/users/$userId"
        )
        if (usernameReq.status != HttpStatusCode.Companion.OK) {
            logger.e(TAG, "Couldn't fetch user info for user id: $userId")
           return null
        } else {
            val userInfo: RobloxUser = usernameReq.body<RobloxUser>()
            logger.d(TAG, "user id: $userId data: $userInfo")
            return userInfo
        }
    }


    suspend fun fetchThumbnailUrl(
        thumbnails: List<RobloxThumbnail>
    ): Map<Long, String>? {
        logger.d(TAG, "fetching thumbnail urls for the following thumbnails: $thumbnails")
        val thumbnailsReq: HttpResponse = httpClient.post(
            "https://thumbnails.roblox.com/v1/batch"
        ) {
            contentType(ContentType.Application.Json)
            setBody(thumbnails)
        }
        if (thumbnailsReq.status != HttpStatusCode.Companion.OK) {
            logger.e(TAG, "failed to fetch thumbnail urls for the following thumbnails: $thumbnails")
            return null
        } else {
            val thumbnailUrls: Map<Long, String> = thumbnailsReq.body<RawRobloxThumbnailResponse>().data.associate {
                it.targetId to it.imageUrl
            }
            logger.d(TAG, "thumbnails: $thumbnails data: $thumbnailUrls")
            return thumbnailUrls
        }
    }

    suspend fun fetchThumbnailUrl(thumbnail: RobloxThumbnail) = fetchThumbnailUrl(listOf(thumbnail))?.values?.first()
}