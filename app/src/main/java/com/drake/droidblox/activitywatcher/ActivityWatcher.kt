package com.drake.droidblox.activitywatcher

import android.os.ParcelFileDescriptor
import com.drake.droidblox.activitywatcher.models.LogEntries
import com.drake.droidblox.roblox.session.RobloxSession
import com.drake.logger.Logger
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityWatcher @Inject constructor(
    val logger: Logger,
    val robloxSession: RobloxSession
) {
    companion object {
        private const val TAG = "ActivityWatcher"
    }

    private suspend fun evaluateLine(line: String) {
//        if (line.contains("[FLog::Output]")) {
//            logger.w(TAG, "Line was printed on Roblox's console, ignoring it!")
//            return
//        }
        try {
            if (line.contains(LogEntries.GameJoiningEntry)) {
                LogEntries.GameJoiningEntryPattern.find(line)?.let { match ->
                    val placeId = match.groups[3]?.value?.toLong()!!
                    val jobId = match.groups[2]?.value!!

                    logger.d(TAG, "Joining Place ID $placeId at Job ID $jobId")

                    robloxSession.gameJoining(placeId = placeId, jobId = jobId)
                }
            } else if (line.contains(LogEntries.GameJoiningUniverseEntry)) {
                LogEntries.GameJoiningUniversePattern.find(line)?.let { match ->
                    val userId = match.groups[2]?.value?.toLong()!!
                    val universeId = match.groups[3]?.value?.toLong()!!

                    logger.d(TAG, "Joining Universe ID $universeId as User ID $userId")

                    robloxSession.universeJoining(userId = userId, universeId = universeId)
                }
            } else if (line.contains(LogEntries.GameJoiningUDMUXEntry)) {
                LogEntries.GameJoiningUDMUXPattern.find(line)?.let { match ->
                    val udmuxIp = match.groups[2]?.value!!

                    logger.d(TAG, "Joining UDMUX IP $udmuxIp")

                    robloxSession.udmuxEntry(ip = udmuxIp)
                }
            } else if (line.contains(LogEntries.GameJoinedEntry)) {
                logger.d(TAG, "Joined game!")

                robloxSession.gameJoined()
            } else if (line.contains(LogEntries.GameMessageEntry)) {
                LogEntries.GameMessageEntryPattern.find(line)?.let { match ->
                    val bloxstrapMsg = match.groups[2]?.value!!

                    robloxSession.gameMessage(message = bloxstrapMsg)
                }
            } else if (line.contains(LogEntries.GameDisconnectedEntry)) {
                logger.d(TAG, "Game disconnected!")

                robloxSession.gameDisconnected()
            }
        } catch (e: Exception) {
            logger.e(TAG, "Something went wrong while trying to evaluate this line: $line\n$e")
        }
    }

    suspend fun robloxDied() {
        logger.i(TAG, "Roblox Died!")
        robloxSession.robloxDied()
    }

    suspend fun readFromPFD(pfd: ParcelFileDescriptor) = withContext(Dispatchers.IO) {
        ParcelFileDescriptor.AutoCloseInputStream(pfd).bufferedReader(Charsets.UTF_8).use { bufferedReader ->
            try {
                logger.d(TAG, "Starting to read from pfd")
                var line = bufferedReader.readLine()
                while (line != null) {
                    evaluateLine(line)
                    line = bufferedReader.readLine()
                }
            } catch (e: CancellationException) {
                logger.d(TAG, "PFD reading cancelled!")
            } catch (e: IOException) {
                logger.e(TAG, "IO Exception occured: $e")
            }
        }
    }
}