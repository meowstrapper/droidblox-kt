package com.drake.droidblox.apiservice

import com.drake.droidblox.logger.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.get

@Singleton
class IpLocationApi @Inject constructor(
    private val httpClient: HttpClient,
    private val logger: Logger
) {
        companion object {
            private const val TAG = "DBIpLocation"
        }

        private var cachedRoSealListOfIP = listOf<Map<String, Any>>()

        private suspend fun fetchIPLocationWithIPInfo(
            ip: String
        ): String? {
            val requestTo = "https://ipinfo.io/$ip/json"

            try {
                logger.d(TAG, "Requesting GET to $requestTo")
                val ipinfoReq = httpClient.get(requestTo)

                if (ipinfoReq.status.value != 200) {
                    logger.e(
                        TAG,
                        "Failed to get the IP location with ip info. Got ${ipinfoReq.status.value}.\nText:\n${ipinfoReq.bodyAsText()}"
                    )
                } else {
                    val location = ipinfoReq.body<Map<String, Any>>()
                    return if (location["city"] == location["region"]) {
                        "${location["region"]}, ${location["country"]}"
                    } else {
                        "${location["city"]}, ${location["region"]}, ${location["country"]}"
                    }
                }
            } catch (e: Exception) {
                logger.e(
                    TAG,
                    "Something went wrong while fetching IP location with ip info!;${e.message}"
                )
            }
            return null
        }

        private suspend fun getIPLocationWithRoSeal(
            ip: String
        ): String? {
            if (cachedRoSealListOfIP.isEmpty()) {
                val requestTo =
                    "https://raw.githubusercontent.com/RoSeal-Extension/Top-Secret-Thing/refs/heads/main/data/datacenters.json"
                try {
                    logger.d(TAG, "Requesting GET to $requestTo")
                    val rosealReq = httpClient.get(requestTo)
                    if (rosealReq.status.value != 200) {
                        logger.e(
                            TAG,
                            "Failed to get the list of ip locations. Got ${rosealReq.status.value}\nText:\n${rosealReq.bodyAsText()}"
                        )
                    } else {
                        cachedRoSealListOfIP = rosealReq.body()
                    }
                } catch (e: Exception) {
                    logger.e(
                        TAG,
                        "Something went wrong while trying to cache the list of ip locations!; ${e.message}"
                    )
                    return null
                }
            }

            for (locationOfIp in cachedRoSealListOfIP) {
                val listOfIps = locationOfIp["ips"] as List<*>
                if (listOfIps.contains(ip)) {
                    val location = locationOfIp["location"] as Map<*, *>
                    return if (location["city"] == location["region"]) {
                        "${location["city"]}, ${location["country"]}"
                    } else {
                        "${location["city"]}, ${location["region"]}, ${location["country"]}"
                    }
                }
            }
            return null
        }

        suspend fun fetchIPLocation(
            ip: String
        ): String? {
            logger.d(TAG, "Attempting to get IP location with RoSeal's list of ips")
            var location = getIPLocationWithRoSeal(ip)
            if (location == null) {
                logger.e(TAG, "Didn't find the IP Location. Falling back to IpInfo.io")
                location = fetchIPLocationWithIPInfo(ip)
                if (location != null) {
                    logger.e(TAG, "Couldn't find the IP location too, returning null!")
                }
            }
            logger.d(TAG, "IP Location: $location")
            return location
        }

}