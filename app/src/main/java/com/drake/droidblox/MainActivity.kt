package com.drake.droidblox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.*
import androidx.activity.enableEdgeToEdge
import com.drake.droidblox.ui.DroidBloxGUI
import com.drake.droidblox.ui.theme.DroidBloxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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