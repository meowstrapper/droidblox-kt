package com.drake.droidblox.apiservice.httpclient

import com.drake.droidblox.logger.Logger
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val TAG = "DBHttpClient"

fun CustomHttpClient(customLogger: Logger) = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    }
    install(Logging) {
        logger = object : io.ktor.client.plugins.logging.Logger {
            override fun log(message: String) {
                customLogger.d(TAG, message)
            }
        }
        level = LogLevel.HEADERS
        filter { request ->
            !request.url.toString().contains("api/v9/users/@me") // this api contains the user's personal info
        }
        sanitizeHeader { header -> header == HttpHeaders.Authorization }
    }
}