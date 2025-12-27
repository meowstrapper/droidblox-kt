// IRobloxSession.aidl
package com.drake.droidblox.backend.activitywatcher;

interface IRobloxSession {
    void joinedServer(long placeId, long universeId, String jobId, String udmuxIp, long userId, long playedAt);
    void leftServer(long leftAt);
}