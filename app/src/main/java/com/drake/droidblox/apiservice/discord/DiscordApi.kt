package com.drake.droidblox.apiservice.discord

import com.drake.droidblox.apiservice.discord.models.DiscordExternalAssetMP
import com.drake.droidblox.apiservice.discord.models.DiscordUserMe
import com.drake.logger.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiscordApi @Inject constructor(
    private val logger: Logger,
    private val httpClient: HttpClient
) {
    companion object {
        private const val TAG = "DiscordApi"
        private const val DROIDBLOX_APPLICATION_ID = 1379313837169311825
    }

    suspend fun fetchMPOfUrls(
        token: String,
        urls: List<String>
    ): List<String>? {
        logger.d(TAG, "fetching media proxy of urls: $urls")
        val mediaProxyReq = httpClient.post(
            "https://discord.com/api/v9/applications/$DROIDBLOX_APPLICATION_ID/external-assets"
        ) {
            header("User-Agent", "ktor")
            contentType(ContentType.Application.Json)
            header("Authorization", token)
            setBody(mapOf("urls" to urls))
        }
        return if (mediaProxyReq.status.value != 200) {
            logger.e(TAG, "failed to failed media proxy of urls: $urls")
            null
        } else {
            mediaProxyReq.body<List<DiscordExternalAssetMP>>().map {
                "mp:${it.externalAssetPath}"
            }
        }

    }

    suspend fun fetchUsername(
        token: String
    ): String? {
        val usernameReq = httpClient.get(
            "https://discord.com/api/v9/users/@me"
        ) {
            header("User-Agent", "ktor")
            header("Authorization", token)
        }
        return if (usernameReq.status.value != 200) {
            null
        } else {
            usernameReq.body<DiscordUserMe>().username
        }

    }

    suspend fun logout(
        token: String
    ) {
        httpClient.post(
            "https://discord.com/api/v9/auth/logout"
        ) {
            header("User-Agent", "ktor")
            contentType(ContentType.Application.Json)
            header("Authorization", token)
            setBody(
                Json.Default.encodeToString(
                mapOf(
                    "provider" to null,
                    "voip_provider" to null
                )
            ))
        }

    }
}