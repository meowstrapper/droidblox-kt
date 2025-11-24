package com.drake.droidblox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.*
import androidx.activity.enableEdgeToEdge
/*import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.drake.droidblox.backend.NotificationHandler
import com.drake.droidblox.backend.launchRoblox
import com.drake.droidblox.backend.suBinaryPath*/
import com.drake.droidblox.frontend.DroidBloxGUI
import com.drake.droidblox.ui.theme.DroidBloxTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //val notifHandler = NotificationHandler(this@MainActivity, "Connected to Server")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroidBloxTheme {
                DroidBloxGUI()
            }
        }
    }
}