package com.drake.droidblox.frontend.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.drake.droidblox.frontend.elements.BasicScreen
import com.drake.droidblox.frontend.elements.RecentGamePlayed

@Composable
fun PlayLogsScreen(
    navController: NavController? = null
) {
    BasicScreen("Play Logs", navController) {
        repeat(10) {
            RecentGamePlayed(
                "RoValra",
                "Valra",
                "https://avatars.githubusercontent.com/u/124619531?v=4",
                System.currentTimeMillis() - 10000,
                System.currentTimeMillis(),
                "roblox://experiences/start?placeId=142823291"
            )
        }
    }
}