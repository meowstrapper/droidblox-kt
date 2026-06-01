package com.drake.droidblox.apiservice.github

import com.drake.droidblox.apiservice.github.models.GithubRelease
import com.drake.logger.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubApi @Inject constructor(
    private val httpClient: HttpClient
) {
    companion object {
        private const val TAG = "GithubApi"
    }

    suspend fun fetchLatestRelease(owner: String, repo: String): GithubRelease = httpClient.get(
        "https://api.github.com/repos/$owner/$repo/releases/latest"
    ).body()
}