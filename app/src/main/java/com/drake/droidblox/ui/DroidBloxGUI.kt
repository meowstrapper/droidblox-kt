package com.drake.droidblox.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.drake.droidblox.ui.view.AboutScreen
import com.drake.droidblox.ui.view.IntegrationsScreen
import com.drake.droidblox.ui.view.LoginToDiscordScreen
//import com.drake.droidblox.ui.view.PlayLogsScreen
import com.drake.droidblox.ui.view.navigation.Routes
import com.drake.droidblox.ui.view.navigation.animatedComposable

@Composable
fun DroidBloxGUI() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.INTEGRATIONS) {
        animatedComposable(Routes.INTEGRATIONS) {
            IntegrationsScreen(navController)
        }
//        animatedComposable(Routes.PLAYLOGS) {
//            PlayLogsScreen(navController)
//        }
        animatedComposable(Routes.LOGIN_TO_DISCORD) {
            LoginToDiscordScreen(navController)
        }
        animatedComposable(Routes.ABOUT) {
            AboutScreen(navController)
        }
    }
}