package com.drake.droidblox.apiservice

import com.drake.droidblox.apiservice.iplocation.IpLocationApi
import com.drake.droidblox.apiservice.rovalrat.RoValraApi
import com.drake.logger.TestLogger
import kotlinx.coroutines.runBlocking
import org.junit.Test

class IpLocationApiTest {
    companion object {
        private const val TAG = "IpLocationApiTest"
    }

    private val logger = TestLogger
    private val httpClient = customHttpClient(logger, "UNIT_TEST")
    private val roValraApi = RoValraApi(
        logger = logger,
        httpClient = httpClient
    )
    private val ipLocationApi = IpLocationApi(
        logger = logger,
        httpClient = httpClient,
        roValraApi = roValraApi
    )

    @Test fun testIpInfo() = runBlocking {
        logger.i(TAG, "Testing IpInfo.io")
        val ipLocation = ipLocationApi.fetchIplocationWithIPInfo("128.116.50.33")
        logger.i(TAG, "IP Location: $ipLocation")
    }

    @Test fun testRoValraIp() = runBlocking {
        logger.i(TAG, "Testing RoValra")
        val ipLocation = roValraApi.fetchIpLocation("128.116.50.33")
        logger.i(TAG, "IP Location: $ipLocation")
    }

    /* you can notice that the ip location of both are not the same
     * and rovalra's ip location tracker is more accurate
     * even though majority of the people who will use this doesn't
     * give a single fuck whether its in the same state or not
     */
}