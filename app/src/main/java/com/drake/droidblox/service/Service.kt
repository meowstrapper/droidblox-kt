package com.drake.droidblox.service

import android.content.Intent
import android.os.IBinder
import android.os.ParcelFileDescriptor
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.drake.IRobloxSession
import com.drake.droidblox.activitywatcher.ActivityWatcher
import com.drake.droidblox.datastores.fastflags.FastFlagsManager
import com.drake.droidblox.datastores.ModsManager
import com.drake.droidblox.datastores.SettingsManager
import com.drake.droidblox.roblox.session.RobloxSession
import com.drake.droidblox.texturemods.TextureMods
import com.drake.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class Service : LifecycleService() {
    companion object {
        private const val TAG = "Service"
    }
    lateinit var logger: Logger
        @Inject set
    lateinit var activityWatcher: ActivityWatcher
        @Inject set
    lateinit var settingsManager: SettingsManager
        @Inject set
    lateinit var fflagsManager: FastFlagsManager
        @Inject set
    lateinit var textureMods: TextureMods
        @Inject set
    lateinit var modsManager: ModsManager
        @Inject set

    private lateinit var pfdReadingJob: Job

    private val binder = object : IRobloxSession.Stub() {
        override fun isMonitoringAllowed(): Boolean = runBlocking {
            settingsManager.getCurrentValue(
                key = SettingsManager.ENABLE_ACTIVITY_TRACKING,
                default = true
            ).also {
                logger.d(TAG, "isMonitoringAllowed() = $it")
            }
        }

        override fun pfdForRobloxLogcat(pfd: ParcelFileDescriptor) {
            logger.d(TAG, "pfdForRobloxLogcat($pfd)")
            pfdReadingJob = lifecycleScope.launch {
                activityWatcher.readFromPFD(pfd)
            }
        }

        override fun getFFlags(): String = runBlocking {
            val applyFFlags = fflagsManager.getCurrentValue(
                key = FastFlagsManager.APPLY_FAST_FLAGS,
                default = true
            )
            if (applyFFlags) {
                fflagsManager.getFFlags()
            } else {
                "{}"
            }.also {
                logger.d(TAG, "getFFlags() = $it")
            }
        }

        override fun getTextureMods(): ParcelFileDescriptor? = runBlocking {
            this@Service.textureMods.zipFolderForPFD().also {
                logger.d(TAG, "getTextureMods() = $it")
            }
        }

        override fun shouldApplyTextureMods(): Boolean = runBlocking {
            modsManager.getCurrentValue(
                key = ModsManager.APPLY_MODS,
                default = true
            ).also {
                logger.d(TAG, "shouldApplyTextureMods() = $it")
            }
        }

        override fun textureModsAlreadyConfigured(): Boolean = runBlocking {
            modsManager.getCurrentValue(
                key = ModsManager.TEXTURE_MODS_ALREADY_CONFIGURED,
                default = false
            ).also {
                logger.d(TAG, "textureModsAlreadyConfigured() = $it")
            }
        }
    }
    
    override fun onBind(intent: Intent): IBinder {
        logger.d(TAG, "onBind()")
        super.onBind(intent)
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        logger.d(TAG, "onUnbind()")
        lifecycleScope.launch(Dispatchers.IO) {
            activityWatcher.robloxDied()
        }
        pfdReadingJob.cancel()
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        logger.d(TAG, "onCreate()")
        //robloxSession.initRpc()
    }
}