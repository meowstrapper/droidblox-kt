package com.drake.droidblox.apiservice

import com.drake.droidblox.apiservice.discord.DiscordApi
import com.drake.logger.TestLogger
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DiscordApiTest {
    companion object {
        private const val TAG = "DiscordApiTest"
    }
    private val logger = TestLogger
    private val discordApi = DiscordApi(logger, customHttpClient(logger, "UNIT_TEST"))
    private val discordToken = System.getenv("DISCORD_TOKEN")!!

    @Test fun testMpOfUrls() = runBlocking {
        logger.i(TAG, "Testing fetch mp of urls")
        val urls = discordApi.fetchMPOfUrls(discordToken, listOf("https://media1.tenor.com/m/zGQLL-kwwEoAAAAd/cat-meme-pee.gif"))
        logger.i(TAG, "media proxies: $urls")
    }

    @Test fun testUsername() = runBlocking {
        logger.i(TAG, "testing username")
        val username = discordApi.fetchUsername(discordToken)
        logger.i(TAG, "username: $username")
    }
}