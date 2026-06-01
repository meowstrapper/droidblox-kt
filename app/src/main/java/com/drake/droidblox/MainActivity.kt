package com.drake.droidblox

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.*
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.drake.droidblox.texturemods.TextureMods
import com.drake.logger.Logger
import com.drake.droidblox.ui.DroidBloxGUI
import com.drake.droidblox.ui.theme.DroidBloxTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    @Inject lateinit var logger: Logger
    @Inject lateinit var notification: Notification
    @Inject lateinit var textureMods: TextureMods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textureMods.initLaunchers(this, lifecycleScope)
        enableEdgeToEdge()
        setContent {
            DroidBloxTheme {
                DroidBloxGUI()
            }
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
//               logger.d(TAG, "Notifications permission is already granted")
//            } else {
//                logger.d(TAG, "Requesting notifications permisssion")
//                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//            }
//        }
        notification.requestPermission(
            activity = this
        )
    }
}