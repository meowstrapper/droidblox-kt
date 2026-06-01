package com.drake.droidblox.apiservice

import com.drake.droidblox.apiservice.rovalrat.RoValraApi
import com.drake.logger.TestLogger
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RoValraApiTest {
    companion object {
        private const val TAG = "RoValraApiTest"
    }

    private val logger = TestLogger
    private val httpClient = customHttpClient(logger, "UNIT_TEST")
    private val roValraApi = RoValraApi(
        logger = logger,
        httpClient = httpClient
    )

    @Test fun testIpLocation() = runBlocking {
        logger.i(TAG, "Testing IP Location")
        val ipLocation = roValraApi.fetchIpLocation("128.116.50.33")
        logger.i(TAG, "IP Location: $ipLocation")
    }

    @Test fun testBetterMatchMaking() = runBlocking {
        logger.i(TAG, "Testing Better Match Making")
        val bestJobId = roValraApi.getBetterMatchmakingJobId(142823291)
        logger.i(TAG, "Nearest Job ID found: $bestJobId")
    }
}