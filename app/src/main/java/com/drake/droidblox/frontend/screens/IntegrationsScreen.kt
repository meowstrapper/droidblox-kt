package com.drake.droidblox.frontend.screens

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.drake.droidblox.DBApplication
import com.drake.droidblox.backend.launchRoblox
import com.drake.droidblox.frontend.elements.BasicScreen
import com.drake.droidblox.frontend.elements.ExtendedButton
import com.drake.droidblox.frontend.elements.ExtendedSwitch
import com.drake.droidblox.frontend.elements.SectionText

@Composable
fun IntegrationsScreen(
    navController: NavController? = null
) {
    val currentActivity = LocalActivity.current as Activity
    val settingsManager = DBApplication.instance.settingsManager

    BasicScreen("Integrations", navController) {
        ExtendedButton(
            "Launch Roblox",
            "Start playing Roblox"
        ) { launchRoblox(currentActivity) }
        SectionText("Activity tracking")
        ExtendedSwitch(
            "Enable activity tracking",
            "Allow DroidBlox to detect what Roblox game you're playing.",
            settingsManager.enableActivityTracking
        ) { settingsManager.enableActivityTracking = it }
        ExtendedSwitch(
            "Query server location",
            "When in game, you'll be able to see where your server is located",
            settingsManager.showServerLocation
        ) { settingsManager.showServerLocation = it }
        SectionText("Discord Rich Presence")
        ExtendedButton(
            "Login to Discord",
            "Login to Discord to show your game activity"
        )
        ExtendedSwitch(
            "Show game activity",
            "The Roblox game you're playing will be show on your Discord profile.",
            settingsManager.showGameActivity
        ) { settingsManager.showGameActivity = it }
        ExtendedSwitch(
            "Allow activity joining",
            "Allows for anybody to join the game you're currently in through your Discord profile.",
            settingsManager.allowActivityJoining
        ) { settingsManager.allowActivityJoining = it }
        ExtendedSwitch(
            "Show Roblox account",
            "Shows the Roblox account you're playing with on your Discord profile.",
            settingsManager.showRobloxUser
        ) { settingsManager.showRobloxUser = it }
    }
}

@Preview
@Composable
private fun Preview() {
    IntegrationsScreen()
}