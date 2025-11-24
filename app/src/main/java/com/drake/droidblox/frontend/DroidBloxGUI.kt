// all screens enclosed in a navigation drawer
package com.drake.droidblox.frontend

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.drake.droidblox.frontend.screens.AboutScreen
import com.drake.droidblox.frontend.screens.IntegrationsScreen
import com.drake.droidblox.frontend.screens.PlayLogsScreen

@Composable
fun DroidBloxGUI() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Integrations") {
        composable(
            "Integrations",
            enterTransition = { fadeIn(tween(300)) },
            exitTransition = { fadeOut(tween(300)) },
            popEnterTransition = { fadeIn(tween(300)) },
            popExitTransition = { fadeOut(tween(300)) },
        ) {
            IntegrationsScreen(navController)
        }
        composable(
            "Play Logs",
            enterTransition = { fadeIn(tween(300)) },
            exitTransition = { fadeOut(tween(300)) },
            popEnterTransition = { fadeIn(tween(300)) },
            popExitTransition = { fadeOut(tween(300)) },
        ) {
            PlayLogsScreen(navController)
        }
        composable(
            "About",
            enterTransition = { fadeIn(tween(300)) },
            exitTransition = { fadeOut(tween(300)) },
            popEnterTransition = { fadeIn(tween(300)) },
            popExitTransition = { fadeOut(tween(300)) },
        ) {
            AboutScreen(navController)
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DroidBloxGUI()
}