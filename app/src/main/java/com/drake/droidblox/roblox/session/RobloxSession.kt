package com.drake.droidblox.roblox.session

import com.drake.droidblox.Notification
import com.drake.droidblox.apiservice.discord.DiscordApi
import com.drake.droidblox.apiservice.iplocation.IpLocationApi
import com.drake.droidblox.apiservice.roblox.RobloxApi
import com.drake.droidblox.apiservice.roblox.models.RobloxThumbnail
import com.drake.droidblox.apiservice.roblox.models.RobloxUser
import com.drake.droidblox.datastores.SettingsManager
import com.drake.droidblox.roblox.models.BloxstrapRPC
import com.drake.droidblox.datastores.playsessions.PlaySession
import com.drake.droidblox.datastores.playsessions.PlaySessionsManager
import com.drake.logger.Logger
import com.my.kizzyrpc.KizzyRPC
import com.my.kizzyrpc.model.Activity
import com.my.kizzyrpc.model.Assets
import com.my.kizzyrpc.model.Metadata
import com.my.kizzyrpc.model.Timestamps
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject

class RobloxSession @Inject constructor(
    private val logger: Logger,
    private val discordApi: DiscordApi,
    private val robloxApi: RobloxApi,
    private val ipLocationApi: IpLocationApi,
    private val playSessionsManager: PlaySessionsManager,
    private val settingsManager: SettingsManager,
    private val notification: Notification
) {
    companion object {
        private const val TAG = "RobloxSession"
        private const val DROIDBLOX_APPLICATION_ID = 1379313837169311825
    }
    private lateinit var kizzyRpc: KizzyRPC
    private var currentActivity: Activity? = null
    private var modifiedActivity: Activity? = null

    private var placeId: Long? = null
    private var joinedAt: Long? = null
    private var jobId: String? = null
    private var userId: Long? = null
    private var universeId: Long? = null
    private var ip: String? = null
    private var lastRpcRequest: Long? = null
    private val assetCache: MutableMap<Long, String> = mutableMapOf()


    suspend fun initRpc() {
        val showGameActivity = settingsManager.getCurrentValue(
            key = SettingsManager.SHOW_GAME_ACTIVITY,
            default = true
        )
        val token = settingsManager.getCurrentValue(
            key = SettingsManager.TOKEN,
            default = ""
        )

        if (showGameActivity && token.isNotEmpty()) {
            val startedAt = System.currentTimeMillis()

            logger.d(TAG, "Initializing kizzy rpc")
            kizzyRpc = KizzyRPC(token)
            kizzyRpc.setActivity(
                activity = Activity(
                    applicationId = DROIDBLOX_APPLICATION_ID.toString(),
                    name = "Roblox",
                    timestamps = Timestamps(
                        start = startedAt
                    )
                )
            )
        }
    }
    suspend fun gameJoining(placeId: Long, jobId: String) {
        this.placeId = placeId
        this.joinedAt = System.currentTimeMillis()
        this.jobId = jobId

        playSessionsManager.edit {
            it[PlaySessionsManager.CURRENT_PLACE_ID] = placeId
            it[PlaySessionsManager.CURRENT_JOB_ID] = jobId
            it[PlaySessionsManager.CURRENT_TIME_OF_JOIN] = this.joinedAt!!
        }
    }

    fun universeJoining(userId: Long, universeId: Long) {
        this.userId = userId
        this.universeId = universeId
    }

    suspend fun udmuxEntry(ip: String) {
        val showServerLocation = settingsManager.getCurrentValue(
            key = SettingsManager.QUERY_SERVER_LOCATION,
            default = false
        )

        if (showServerLocation) {
            logger.d(TAG, "Fetching IP Location")
            val ipLocation = ipLocationApi.fetchIPLocation(ip)
            if (ipLocation == null) {
                logger.e(TAG, "Something went wrong while trying to fetch the ip location! Stopping action.")
                return
            }
            logger.d(TAG, "Notifying")
            notification.notify("Connecting to server", "Located at $ipLocation")
         }
    }

    suspend fun gameJoined() {
        val token = settingsManager.getCurrentValue(
            key = SettingsManager.TOKEN,
            default = ""
        )
        val showGameActivity = settingsManager.getCurrentValue(
            key = SettingsManager.SHOW_GAME_ACTIVITY,
            default = true
        )
        val showRobloxUser = settingsManager.getCurrentValue(
            key = SettingsManager.SHOW_ROBLOX_USER,
            default = false
        )
        val allowActivityJoining = settingsManager.getCurrentValue(
            key = SettingsManager.ALLOW_ACTIVITY_JOINING,
            default = false
        )

        if (!(::kizzyRpc.isInitialized && token.isEmpty() && showGameActivity)) {
            return
        }

        try {
            logger.d(TAG, "Fetching game info")
            val gameInfo = robloxApi.fetchGameInfo(universeId!!)
            if (gameInfo == null) {
                logger.e(TAG, "Something went wrong with Roblox's Game API! Stopping action.")
                return
            }
            val gameName = gameInfo.name
            val gameCreator = gameInfo.creator.name + if (gameInfo.creator.isVerified) " ☑\uFE0F" else ""

            logger.d(TAG, "Fetching game and thumbnail url")
            val thumbnailUrls = robloxApi.fetchThumbnailUrl(
                listOf(
                    RobloxThumbnail(
                        targetId = universeId!!,
                        type = "GameIcon",
                        size = "512x512",
                        isCircular = false
                    )
                ) + if (showRobloxUser)
                    listOf(
                        RobloxThumbnail(
                            targetId = userId!!,
                            type = "AvatarHeadShot",
                            size = "75x75",
                            isCircular = true
                        )
                    ) else emptyList()
            )?.values?.toList()
            if (thumbnailUrls == null) {
                logger.e(TAG, "Something went wrong on Roblox's Thumbnail API! Stopping action.")
                return
            }

            logger.d(TAG, "Fetching media proxies")
            val mpUrls = discordApi.fetchMPOfUrls(token, thumbnailUrls)
            if (mpUrls == null) {
                logger.e(TAG, "Something went wrong on Discord's Media Proxy API! Stopping action.")
                return
            }

            var userInfo: RobloxUser? = null
            if (showRobloxUser) {
                logger.d(TAG, "Fetching user info")
                userInfo = robloxApi.fetchUserInfo(userId!!)
                if (userInfo == null) {
                    logger.e(TAG, "Something went wrong on Roblox's User API! Stopping action.")
                    return
                }
            }

            logger.d(TAG, "Setting RPC")
            currentActivity = Activity(
                applicationId = DROIDBLOX_APPLICATION_ID.toString(),
                name = "Roblox",
                details = gameName,
                state = "by $gameCreator",
                timestamps = Timestamps(
                    start = joinedAt
                ),
                assets = Assets(
                    largeImage = mpUrls[0],
                    largeText = gameName,
                    smallImage = if (showRobloxUser) mpUrls[1] else null,
                    smallText = if (showRobloxUser) "Playing on ${userInfo!!.name} (@${userInfo.displayName})" else null
                ),
                buttons = listOf("See game page") + if (allowActivityJoining) listOf(
                    "Join server"
                ) else emptyList(),
                metadata = Metadata(
                    buttonUrls = listOf("https://roblox.com/games/$placeId") + if (allowActivityJoining) listOf(
                        "https://roblox.com/games/start?placeId=$placeId&gameInstanceId=$jobId"
                    ) else emptyList()
                )
            )
            kizzyRpc.setActivity(
                activity = currentActivity!!
            )
        } catch (e: Exception) {
            logger.e(TAG, "Something went wrong while trying to handle game join: $e")
            return
        }
    }

    suspend fun gameMessage(message: String) {if (!::kizzyRpc.isInitialized) { return }
        if (lastRpcRequest != null && System.currentTimeMillis() - lastRpcRequest!! <= 1) {
            logger.d(TAG, "Dropping message as ratelimit has been hit")
            return
        }

        val token = settingsManager.getCurrentValue(
            key = SettingsManager.TOKEN,
            default = ""
        )

        val json = Json { ignoreUnknownKeys = true }
        val serializedMessage = json.parseToJsonElement(message).jsonObject
        val command = serializedMessage["command"]?.jsonPrimitive?.content
        if (command == null) {
            logger.d(TAG, "Missing command! This message is corrupted. Stopping action!")
            return
        }
        val data = serializedMessage["data"]
        if (data == null) {
            logger.d(TAG, "Missing data! This message is corrupted. Stopping action!")
            return
        }

        when (command) {
            "SetLaunchData" -> {
                val launchData = data.jsonPrimitive.content
                val activityToSet = if (modifiedActivity != null) modifiedActivity else currentActivity

                logger.d(TAG, "Setting launch data to $launchData")
                kizzyRpc.setActivity( // any way to do this better?
                    activity = Activity(
                        applicationId = activityToSet?.applicationId,
                        name = activityToSet?.name,
                        state = activityToSet?.state,
                        details = activityToSet?.details,
                        type = activityToSet?.type,
                        timestamps = activityToSet?.timestamps,
                        assets = activityToSet?.assets,
                        buttons = activityToSet?.buttons,
                        metadata = Metadata(
                            buttonUrls = listOf(
                                activityToSet?.metadata?.buttonUrls?.get(0),
                                activityToSet?.metadata?.buttonUrls?.get(1) + "&launchData=$launchData"
                            )
                        )
                    )
                )
            }
            "SetRichPresence" -> {
                val rpcMessage = json.decodeFromJsonElement<BloxstrapRPC>(data)
                var details: String? = null
                var state: String? = null
                var timeStart: Long? = null
                var timeEnd: Long? = null
                var smallImageUrl: String? = null
                var smallImageText: String? = null
                var largeImageUrl: String? = null
                var largeImageText: String? = null

                rpcMessage.details?.let {
                    if (it.length > 128) {
                        logger.w(TAG, "Details cannot be longer than 128 characters, ignoring")
                    } else if (it == "<reset>") {
                        details = currentActivity?.details
                    } else {
                        details = it
                    }
                }
                rpcMessage.state?.let {
                    if (it.length > 128) {
                        logger.w(TAG, "State cannot be longer than 128 characters, ignoring")
                    } else if (it == "<reset>") {
                        state = currentActivity?.state
                    } else {
                        state = it
                    }
                }

                rpcMessage.timeStart?.let {
                    timeStart = if (it == 0L) {
                        null
                    } else {
                        it * 1000L
                    }
                }
                rpcMessage.timeEnd?.let {
                    timeEnd = if (it == 0L) {
                        null
                    } else {
                        it * 1000L
                    }
                }

                rpcMessage.smallImage?.let {
                    if (it.clear == true) {
                        smallImageUrl = null
                    } else if (it.reset == true) {
                        smallImageUrl = currentActivity?.assets?.smallImage
                        smallImageText = currentActivity?.assets?.smallText
                    } else {
                        smallImageText = it.hoverText

                        if (assetCache.containsKey(it.assetId!!)) {
                            smallImageUrl = assetCache[it.assetId]
                        } else {
                            logger.d(TAG, "Fetching small image url for asset id ${it.assetId}")
                            val smallImage = robloxApi.fetchThumbnailUrl(
                                RobloxThumbnail(
                                    targetId = it.assetId,
                                    type = "Asset",
                                    size = "75x75",
                                    isCircular = false
                                )
                            )
                            if (smallImage == null) {
                                logger.e(TAG, "Something went wrong on Roblox's Thumbnail API! Stopping action")
                                return
                            }

                            logger.d(TAG, "Fetching media proxy for thumbnail url $smallImage")
                            val smallImageMp = discordApi.fetchMPOfUrls(
                                token = token,
                                urls = listOf(smallImage)
                            )
                            if (smallImageMp == null) {
                                logger.e(TAG, "Something went wrong on Discord's Media Proxy API! Stopping action.")
                                return
                            }
                            smallImageUrl = smallImageMp[0]
                        }
                    }
                }
                rpcMessage.largeImage?.let {
                    if (it.clear == true) {
                        largeImageUrl = null
                    } else if (it.reset == true) {
                        largeImageUrl = currentActivity?.assets?.largeImage
                        largeImageText = currentActivity?.assets?.largeText
                    } else {
                        largeImageText = it.hoverText

                        if (assetCache.containsKey(it.assetId!!)) {
                            largeImageUrl = assetCache[it.assetId]
                        } else {
                            logger.d(TAG, "Fetching large image url for asset id ${it.assetId}")
                            val largeImage = robloxApi.fetchThumbnailUrl(
                                RobloxThumbnail(
                                    targetId = it.assetId,
                                    type = "Asset",
                                    size = "75x75",
                                    isCircular = false
                                )
                            )
                            if (largeImage == null) {
                                logger.e(TAG, "Something went wrong on Roblox's Thumbnail API! Stopping action")
                                return
                            }

                            logger.d(TAG, "Fetching media proxy for thumbnail url $largeImage")
                            val largeImageMp = discordApi.fetchMPOfUrls(
                                token = token,
                                urls = listOf(largeImage)
                            )
                            if (largeImageMp == null) {
                                logger.e(TAG, "Something went wrong on Discord's Media Proxy API! Stopping action.")
                                return
                            }
                            largeImageUrl = largeImageMp[0]
                        }
                    }
                }

                logger.d(TAG, "Changing activity")
                modifiedActivity = Activity(
                    applicationId = currentActivity?.applicationId,
                    name = currentActivity?.name,
                    state = state,
                    details = details,
                    type = currentActivity?.type,
                    timestamps = Timestamps(
                        start = timeStart,
                        end = timeEnd
                    ),
                    assets = Assets(
                        largeImage = largeImageUrl,
                        largeText = largeImageText,
                        smallImage = smallImageUrl,
                        smallText = smallImageText
                    ),
                    buttons = currentActivity?.buttons,
                    metadata = currentActivity?.metadata
                )
                kizzyRpc.setActivity(activity = modifiedActivity!!)
            }
        }
    }

    suspend fun gameDisconnected() {
        val logPlaySessions = playSessionsManager.getCurrentValue(
            key = PlaySessionsManager.LOG_PLAY_SESSIONS,
            default = true
        )

        if (logPlaySessions) {
            logger.d(TAG, "Logging play session")
            logPlaySession()
        }

        logger.d(TAG, "Resetting variables")
        resetAll()

    }

    suspend fun robloxDied() {
        val logPlaySessions = playSessionsManager.getCurrentValue(
            key = PlaySessionsManager.LOG_PLAY_SESSIONS,
            default = true
        )

        if (universeId != null) { // roblox died while playing
            if (logPlaySessions) {
                logger.d(TAG, "Logging play session")
                logPlaySession()
            }
        }

        resetAll()

        if (::kizzyRpc.isInitialized) {
            logger.d(TAG, "Killing KizzyRPC")
            kizzyRpc.closeRPC()
        }
    }

    suspend fun logPlaySession() = playSessionsManager.appendPlaySession(
        PlaySession(
            universeId = universeId!!,
            playedAt = joinedAt!!,
            leftAt = System.currentTimeMillis(),
            placeId = placeId!!,
            jobId = jobId!!
        )
    )

    suspend fun resetAll() {
        placeId = null
        joinedAt = null
        jobId = null
        userId = null
        universeId = null
        ip = null
        lastRpcRequest = null
        modifiedActivity = null
        currentActivity = null

        playSessionsManager.edit {
            it[PlaySessionsManager.CURRENT_PLACE_ID] = 0
            it[PlaySessionsManager.CURRENT_JOB_ID] = ""
            it[PlaySessionsManager.CURRENT_TIME_OF_JOIN] = 0
        }
    }
}