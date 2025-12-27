package com.drake.droidblox.apiservice

import com.drake.droidblox.logger.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Singleton
class DiscordApi @Inject constructor(
    private val logger: Logger,
    private val httpClient: HttpClient
) {
    companion object {
        private const val TAG = "DBDiscordAPI"
        private const val DROIDBLOX_APPLICATION_ID = 1379313837169311825
    }
    suspend fun fetchMPOfUrls(
        token: String?,
        urls: List<String>
    ): List<String>? {
        val requestTo =
            "https://discord.com/api/v9/applications/$DROIDBLOX_APPLICATION_ID/external-assets"
        val jsonToSend = Json.encodeToString(mapOf("urls" to urls))

        try {
            logger.d(TAG, "Requesting POST to $requestTo with json $jsonToSend")

            val mediaProxyReq = httpClient.post(requestTo) {
                contentType(ContentType.Application.Json)
                header("Authorization", token)
                setBody(jsonToSend)
            }
            if (mediaProxyReq.status.value != 200) {
                logger.e(
                    TAG,
                    "Failed to get media proxy of urls. Got ${mediaProxyReq.status.value}\nText:\n${mediaProxyReq.bodyAsText()}"
                )
            } else {
                return mediaProxyReq.body<List<Map<String, Any>>>().mapNotNull {
                    "mp:${it["external_asset_path"]}" as String?
                }
            }
        } catch (e: Exception) {
            logger.e(TAG, "Something went wrong while fetching media proxy of urls!; ${e.message}")
        }
        return null
    }

    suspend fun fetchUsername(
        token: String
    ): String? {
        val requestTo = "https://discord.com/api/v9/users/@me"

        try {
            logger.d(TAG, "Requesting GET to $requestTo")

            val usernameReq = httpClient.get(requestTo) {
                header("Authorization", token)
            }
            if (usernameReq.status.value != 200) {
                logger.e(
                    TAG,
                    "Failed to fetch the discord username of token. Got ${usernameReq.status.value}\nText:\n${usernameReq.bodyAsText()}"
                )
            } else {
                return usernameReq.body<Map<String, Any>>()["username"] as? String
            }
        } catch (e: Exception) {
            logger.e(TAG, "Something went wrong while fetching username!; ${e.message}")
        }
        return null
    }

    suspend fun logout(
        token: String
    ) {
        val requestTo = "https://discord.com/api/v9/auth/logout"
        val jsonToSend = Json.encodeToString(
            mapOf(
                "provider" to null,
                "voip_provider" to null
            )
        )
        try {
            logger.d(TAG, "Requesting post to $requestTo with json $jsonToSend")

            val logoutReq = httpClient.post(requestTo) {
                contentType(ContentType.Application.Json)
                header("Authorization", token)
                setBody(jsonToSend)
            }
            if (logoutReq.status.value != 204) {
                logger.e(
                    TAG,
                    "Failed to logout discord token! Got ${logoutReq.status.value}\nText:\n${logoutReq.bodyAsText()}"
                )
            }
        } catch (e: Exception) {
            logger.e(TAG, "Something went wrong while logging out!; ${e.message}")
        }
    }
}