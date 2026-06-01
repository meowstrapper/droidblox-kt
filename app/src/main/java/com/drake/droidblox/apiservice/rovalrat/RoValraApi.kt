package com.drake.droidblox.apiservice.rovalrat

import com.drake.droidblox.apiservice.iplocation.models.IpLocation
import com.drake.droidblox.apiservice.rovalrat.models.RawRoValraIpLocation
import com.drake.droidblox.apiservice.rovalrat.models.RawRoValraServers
import com.drake.droidblox.apiservice.rovalrat.models.RoValraDatacenter
import com.drake.droidblox.apiservice.rovalrat.models.RoValraIpLocation
import com.drake.droidblox.apiservice.rovalrat.models.RoValraServer
import com.drake.logger.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Singleton
class RoValraApi @Inject constructor(
    val logger: Logger,
    val httpClient: HttpClient
) {
    companion object {
        private const val TAG = "RoValraApi"
    }

    suspend fun fetchIpLocation(
        ip: String
    ): String? {
        logger.d(TAG, "Fetching ip location $ip")
        val roValraIplocationReq = httpClient.get(
            "https://apis.rovalra.com/v1/geolocation?ip=$ip"
        )
        if (roValraIplocationReq.status != HttpStatusCode.OK) {
            logger.e(TAG, "Failed to fetch ip location $ip")
            return null
        }
        val location: RoValraIpLocation = roValraIplocationReq.body<RawRoValraIpLocation>().location
        return if (location.city == location.region) {
            "${location.city}, ${location.country}"
        } else {
            "${location.city}, ${location.region}, ${location.country}"
        }
    }
    private fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        // https://github.com/fishstrap/fishstrap/blob/33e81d74330b0221a005682e7e702ede02e2d4c5/Bloxstrap/Bootstrapper.cs#L635
        // i think rovalra made the original code..... heh...

        val R = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2) // what the fuck is rovalra writing
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return R * c
    }

    suspend fun getBetterMatchmakingJobId(placeId: Long): String? {
        // https://github.com/fishstrap/fishstrap/blob/33e81d74330b0221a005682e7e702ede02e2d4c5/Bloxstrap/Bootstrapper.cs#L650

        val ipInfoReq = httpClient.get(
            "https://ipinfo.io/json"
        )
        if (ipInfoReq.status != HttpStatusCode.OK) {
            logger.e(TAG, "Something went wrong while trying to fetch ipinfo!")
            return null
        }
        val userIpLocation: IpLocation = ipInfoReq.body()

        val roValraDatacentersReq = httpClient.get(
            "https://apis.rovalra.com/v1/datacenters/list"
        )
        if (roValraDatacentersReq.status != HttpStatusCode.OK) {
            logger.e(TAG, "Something went wrong while trying to fetch RoValra's datacenters list!")
            return null
        }
        val rovalraDatacenters: List<RoValraDatacenter> = roValraDatacentersReq.body()

        val location: List<String> = userIpLocation.loc.split(",")
        val lat1: Double = location[0].toDouble()
        val lon1: Double = location[1].toDouble()

        var regions: MutableList<String> = rovalraDatacenters
            .sortedBy { getDistance(lat1, lon1, it.location.latLong[0].toDouble(), it.location.latLong[1].toDouble()) }
            .map { it.location.country }
            .distinct()
            .toMutableList()

        if (regions.contains(userIpLocation.country)) {
            regions.remove(userIpLocation.country)
            regions.add(0, userIpLocation.country)
        }

        for (region in regions) {
            logger.d(TAG, "Checking for servers in user region")

            val roValraServersReq = httpClient.get(
                "https://apis.rovalra.com/v1/servers/region?place_id=$placeId&region=$region"
            )
            if (roValraServersReq.status != HttpStatusCode.OK) {
                logger.e(TAG, "Something went wrong while trying to fetch regions, stopping action!")
                return null
            }
            val roValraServers: List<RoValraServer> = roValraServersReq.body<RawRoValraServers>().servers

            if (roValraServers.count() > 0) {
                // TODO enablebettermatchmakingrandomization

                return roValraServers[0].serverId
            }
            logger.w(TAG, "No servers available in user region, moving on to the next")
        }

        logger.w(TAG, "No servers found!")
        return null
    }
}