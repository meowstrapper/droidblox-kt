package com.drake.droidblox.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.drake.droidblox.ui.view.views.about.AboutScreen
import com.drake.droidblox.ui.view.navigation.Routes
import com.drake.droidblox.ui.view.navigation.animatedComposable
import com.drake.droidblox.ui.view.views.fastflags.FFlagsScreenImpl
import com.drake.droidblox.ui.view.views.integrations.IntegrationsScreenImpl
import com.drake.droidblox.ui.view.views.mods.ModsScreenImpl
import com.drake.droidblox.ui.view.views.playlogs.PlayLogsScreenImpl

@Composable
fun DroidBloxGUI() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.INTEGRATIONS
        ) {
            animatedComposable(Routes.INTEGRATIONS) {
                IntegrationsScreenImpl(
                    navController = navController
                )
            }

//        animatedComposable(Routes.LOGIN_TO_DISCORD) {
//            LoginToDiscordScreen(
//                navController = navController
//            )
//        }
            animatedComposable(Routes.FFLAGS) {
                FFlagsScreenImpl(
                    navController = navController
                )
            }
            animatedComposable(Routes.MODS) {
                ModsScreenImpl(
                    navController = navController
                )
            }
            animatedComposable(Routes.PLAYLOGS) {
                PlayLogsScreenImpl(
                    navController = navController
                )
            }
            animatedComposable(Routes.ABOUT) {
                AboutScreen(
                    navController = navController
                )
            }
        }
    }
}