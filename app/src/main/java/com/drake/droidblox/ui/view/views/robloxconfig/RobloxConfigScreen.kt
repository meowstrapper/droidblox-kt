package com.drake.droidblox.ui.view.views.robloxconfig

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.drake.droidblox.ui.components.BasicScreen
import com.drake.droidblox.ui.components.ExtendedButton
import com.drake.droidblox.ui.components.SectionText
import com.drake.droidblox.ui.view.views.robloxconfig.state.RobloxConfigCallbacks
import com.drake.droidblox.ui.view.views.robloxconfig.state.RobloxConfigState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("ConstantLocale")
private val dateFormat = SimpleDateFormat("HH:mm MM/dd/yyyy", Locale.getDefault())

@Composable
fun RobloxConfigScreen(
    navController: NavController? = null,
    state: RobloxConfigState = RobloxConfigState(),
    callbacks: RobloxConfigCallbacks = RobloxConfigCallbacks()
) {
    /*
    1. Roblox status (card)
        * if already built and is installed:
            - show version
            - show the last time that it was launched
            - show if its out to date
            - buttons: Launch, Update (if out to date), Uninstall
        * if not:
            - show "Start building to build a version of Roblox for DroidBlox."
            - buttons: Build
    Section text: Configure Build
    2. Configure key
        - Choose which keystore to use while building.
        - Shows a dialog with the following:
            1. Choose keystore file with saf
            2. Enter password (check if its valid right after)
            3. Save button
     */
    val isInstalled = !(state.robloxVersion.isNullOrEmpty())
    BasicScreen(
        name = "Roblox Configuration",
        navController = navController
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Roblox",
                        style = MaterialTheme.typography.titleMedium
                    )

                    if (isInstalled) {
                        val lastLaunchedDate = remember {
                            dateFormat.format(Date(state.lastLaunched!!))
                        }
                        Text(
                            text = "Version: ${state.robloxVersion}\n" +
                                    "Last launched: ${lastLaunchedDate}"
                        )
                    }
                    Text(
                        text = "Version: 2.706.6767\n" +
                                "Last launched: 06:07 06/07/2025",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = callbacks.launchRoblox
                    ) {
                        Text("Launch")
                    }
                    Button(
                        onClick = callbacks.uninstallRoblox,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        )
                    ) {
                        Text("Uninstall")
                    }
                }
            }
        }

        SectionText("Configure Patch")

        ExtendedButton(
            title = "Configure Keystore",
            subtitle = "Open a keystore file to use while patching",
            onClick = callbacks.configureKeystore
        )
    }
}

@Preview
@Composable
private fun RobloxConfigScreenPreview() {
    RobloxConfigScreen()
}