// IRobloxSession.aidl
package com.drake;

// Declare any non-default types here with import statements
import android.os.ParcelFileDescriptor;

interface IRobloxSession {
    boolean isMonitoringAllowed();

    void pfdForRobloxLogcat(in ParcelFileDescriptor pfd);

    String getFFlags();

    ParcelFileDescriptor getTextureMods();
    boolean shouldApplyTextureMods();
    boolean textureModsAlreadyConfigured();
}