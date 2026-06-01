package com.drake.droidblox.activitywatcher.models

object LogEntries {
    val GameMessageEntry                    = "[FLog::Output] [BloxstrapRPC]"
    val GameJoiningEntry                    = "[FLog::Output] ! Joining game"
    // private val GameTeleportingEntry                = "[FLog::GameJoinUtil] GameJoinUtil::initiateTeleportToPlace"
    // private val GameJoiningPrivateServerEntry       = "[FLog::GameJoinUtil] GameJoinUtil::joinGamePostPrivateServer"
    // private val GameJoiningReservedServerEntry      = "[FLog::GameJoinUtil] GameJoinUtil::initiateTeleportToReservedServer"
    val GameJoiningUniverseEntry            = "[FLog::GameJoinLoadTime] Report game_join_loadtime:"
    val GameJoiningUDMUXEntry               = "[FLog::Network] UDMUX Address = "
    val GameJoinedEntry                     = "[FLog::Network] serverId:"
    val GameDisconnectedEntry               = "[FLog::Network] Time to disconnect replication data:"

    val GameJoiningEntryPattern             = Regex("""(! Joining game '([0-9a-f\-]{36})' place ([0-9]+) at ([0-9.]+))""")
    val GameJoiningUniversePattern          = Regex("""(userid:([0-9]+), .*universeid:([0-9]+))""")
    val GameJoiningUDMUXPattern             = Regex("""(UDMUX Address = ([0-9.]+))""")
    val GameMessageEntryPattern             = Regex("""(\[BloxstrapRPC] (.*))""")
}