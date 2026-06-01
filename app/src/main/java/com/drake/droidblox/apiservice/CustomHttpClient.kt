package com.drake.droidblox.apiservice

import com.drake.logger.Logger
import io.ktor.client.HttpClient
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val TAG = "HttpClient"

fun customHttpClient(customLogger: Logger, appVersion: String) = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    }
    install(UserAgent) {
        agent = "Drakestrap/$appVersion (DroidBlox)"
    }
    install(Logging) {
        logger = object : io.ktor.client.plugins.logging.Logger {
            override fun log(message: String) {
                customLogger.d(TAG, message)
            }
        }
        level = LogLevel.ALL
        filter { request ->
            !(request.url.toString().contains("api/v9/users/@me") || request.url.toString().contains("RoSeal")) // this api contains the user's personal info

        }
        sanitizeHeader { header -> header == HttpHeaders.Authorization }
    }
}