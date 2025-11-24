package com.drake.droidblox.backend.activitywatcher.models

object LogEntries {
    const val GameJoiningEntry = "[FLog::Output] ! Joining game"
    const val GameMessageEntry = "[FLog::Output] [BloxstrapRPC]"
    const val GameTeleportingEntry = "[FLog::GameJoinUtil] GameJoinUtil::initiateTeleportToPlace"
    const val GameJoiningPrivateServerEntry =
        "[FLog::GameJoinUtil] GameJoinUtil::joinGamePostPrivateServer"
    const val GameJoiningReservedServerEntry =
        "[FLog::GameJoinUtil] GameJoinUtil::initiateTeleportToReservedServer"
    const val GameJoiningUniverseEntry = "[FLog::GameJoinLoadTime] Report game_join_loadtime:"
    const val GameJoiningUDMUXEntry = "[FLog::Network] UDMUX Address = "
    const val GameJoinedEntry = "[FLog::Network] serverId:"
    const val GameDisconnectedEntry = "[FLog::Network] Time to disconnect replication data:"
    const val GameLeavingEntry = "[FLog::SingleSurfaceApp] leaveUGCGameInternal"
    const val RobloxExitedEntry = "[FLog::SingleSurfaceApp] destroyLuaApp"

    val GameJoiningEntryPattern =
        Regex("""! Joining game '([0-9a-f\-]{36})' place ([0-9]+) at ([0-9\.]+)""")
    val GameJoiningPrivateServerPattern = Regex("""accessCode"":""([0-9a-f\-]{36})""")
    val GameJoiningUniversePattern = Regex("""userid:([0-9]+), .*universeid:([0-9]+)""")
    val GameJoiningUDMUXPattern = Regex("""UDMUX Address = ([0-9\.]+)""")
    val GameJoinedEntryPattern = Regex("""serverId: ([0-9\.]+)\|[0-9]+""")
    val GameMessageEntryPattern = Regex("""\[BloxstrapRPC\] (.*)""")
}