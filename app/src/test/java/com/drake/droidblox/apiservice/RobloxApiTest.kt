package com.drake.droidblox.apiservice

import com.drake.droidblox.apiservice.roblox.models.RobloxThumbnail
import com.drake.droidblox.apiservice.roblox.RobloxApi
import com.drake.logger.TestLogger
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RobloxApiTest {
    companion object {
        private const val TAG = "RobloxApiTest"
    }

    val logger = TestLogger
    val robloxApi = RobloxApi(
        logger = logger,
        httpClient = customHttpClient(TestLogger, "UNIT_TEST")

    )
    @Test fun testUserInfo() = runBlocking {
        logger.i(TAG, "Testing user info")
        val userInfo = robloxApi.fetchUserInfo(1)
        logger.i(TAG, "User Info: $userInfo")
    }

    @Test fun testGameInfo() = runBlocking {
        logger.i(TAG, "Testing game info")
        val gameInfo = robloxApi.fetchGameInfo(66654135) // murder mystery 2
        logger.i(TAG, "Game Info: $gameInfo")
    }

    @Test fun testThumbnailUrl() = runBlocking {
        logger.i(TAG, "Testing thumbnail url")
        val thumbnailUrl = robloxApi.fetchThumbnailUrl(RobloxThumbnail(
            targetId = 1,
            type = "AvatarHeadshot",
            size = "75x75",
            isCircular = false
        ))
        logger.i(TAG, "Thumbnail url: $thumbnailUrl")
    }
}