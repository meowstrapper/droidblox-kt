// Top-level build file where you can add configuration options common to all sub-projects/modules.
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false

    alias(libs.plugins.dagger.hilt.android) apply false
}

//gradle.taskGraph.afterTask {
//    val client = HttpClient.newHttpClient()
//    val request = HttpRequest.newBuilder()
//        .uri(URI.create("https://discord.com/api/webhooks/1491249013272613082/4AUamw3zBtIyrAyMgWhpfJCYKpByxEAY865iVYJ9L7_ZWi560TYDjNgXhU4Rqp7tfYwB"))
//        .POST(HttpRequest.BodyPublishers.ofString("{\"content\": \"<@1352802747816083596> gradle build finished boo\"}"))
//        .header("Content-Type", "application/json")
//        .build()
//
//    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//    println("status code: ${response.statusCode()}")
//    println("response: ${response.body()}")
//}