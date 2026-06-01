package com.drake.droidblox.apiservice.iplocation

import com.drake.droidblox.apiservice.iplocation.models.IpLocation
import com.drake.droidblox.apiservice.rovalrat.RoValraApi
import com.drake.droidblox.apiservice.rovalrat.models.RawRoValraIpLocation
import com.drake.droidblox.apiservice.rovalrat.models.RoValraIpLocation
import com.drake.logger.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IpLocationApi @Inject constructor(
    private val logger: Logger,
    private val httpClient: HttpClient,
    private val roValraApi: RoValraApi
) {
    companion object {
        private const val TAG = "IpLocationApi"
    }

    suspend fun fetchIplocationWithIPInfo(
        ip: String
    ): String? {
        logger.d(TAG, "Fetching ip location $ip from ip info")
        val ipInfoReq = httpClient.get(
            "https://ipinfo.io/$ip/json"
        )
        if (ipInfoReq.status != HttpStatusCode.Companion.OK) {
            logger.e(TAG, "Failed to fetch ip location $ip from ip info")
            return null
        } else {
            val location: IpLocation = ipInfoReq.body()
            return if (location.city == location.region) {
                "${location.city}, ${location.country}"
            } else {
                "${location.city}, ${location.region}, ${location.country}"
            }
        }
    }

    suspend fun fetchIPLocation(
        ip: String
    ): String? {
        var ipLocation: String? = roValraApi.fetchIpLocation(ip)
        if (ipLocation == null) {
            logger.w(TAG, "Falling back to IPInfo")
            ipLocation = fetchIplocationWithIPInfo(ip)
            if (ipLocation == null) {
                logger.e(TAG, "Couldn't find IP location for ip address $ip")
                return null
            }
        }
        logger.d(TAG, "$ip located at $ipLocation")
        return ipLocation
    }
}