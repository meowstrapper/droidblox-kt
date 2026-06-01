package com.drake.droidblox

import com.drake.droidblox.apiservice.discord.DiscordApi
import com.drake.droidblox.apiservice.iplocation.IpLocationApi
import com.drake.droidblox.apiservice.roblox.RobloxApi
import com.drake.droidblox.apiservice.customHttpClient
import com.drake.logger.TestLogger
import com.drake.droidblox.roblox.session.RobloxSession
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/*
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [26])
class RobloxSessionTest {
    companion object {
        private const val TAG = "RobloxSessionTest"
    }

    private val context = RuntimeEnvironment.getApplication()
    private val logger = TestLogger
    private val httpClient = customHttpClient(logger, "UNIT_TEST")
    private val discordApi = DiscordApi(logger, httpClient)
    private val robloxApi = RobloxApi(logger, httpClient)
    private val ipLocationApi = IpLocationApi(logger, httpClient)
    private val playSessionsManager = PlaySessionsManager(logger, context)
    private val settingsManager = SettingsManager(logger, context)
    private val notification = Notification(logger, context)
    private val robloxSession = RobloxSession(context, logger, discordApi, robloxApi, ipLocationApi, playSessionsManager, settingsManager, notification)

    @Test fun testAll() = runBlocking {
        //ShadowLog.stream = System.out

        logger.i(TAG, "initializing settings")
        settingsManager.showServerLocation = true
        settingsManager.allowActivityJoining = true
        settingsManager.showRobloxUser = true

        logger.i(TAG, "Testing init rpc")
        robloxSession.initRpc()

        logger.i(TAG, "Testing game joining entry")
        robloxSession.gameJoining(142823291, "67-67-67-67 TEST")

        logger.i(TAG, "Testing universe joining")
        robloxSession.universeJoining(1, 66654135)

        logger.i(TAG, "Testing udmux entry")
        robloxSession.udmuxEntry("128.116.97.33")

        logger.i(TAG, "Testing game joined")
        robloxSession.gameJoined()

        logger.i(TAG, "Testing BloxstrapRPC 1")
        robloxSession.gameMessage("{\"command\": \"SetRichPresence\", \"data\": {\"details\": \"meowwers!!\", \"state\": \"testing bloxstraprpc\", \"timeStart\": 1774337668136, \"largeImage\": {\"assetId\": 11176073582, \"hoverText\": \"meowers\", \"clear\": false, \"reset\": false}}}")

        logger.i(TAG, "Testing BloxstrapRPC 2")
        robloxSession.gameMessage("{\"command\": \"SetLaunchData\", \"data\": \"returnisafurry\"}")

        while (true) {

        }
    }
}
 */