package com.drake.droidblox.ui.view.views.integrations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.drake.droidblox.datastores.SettingsManager
import com.drake.droidblox.ui.components.BasicScreen
import com.drake.droidblox.ui.components.ExtendedButton
import com.drake.droidblox.ui.components.ExtendedSwitch
import com.drake.droidblox.ui.components.SectionText
import com.drake.droidblox.ui.view.views.integrations.state.IntegrationsScreenCallbacks
import com.drake.droidblox.ui.view.views.integrations.vm.IntegrationsScreenVM
import com.drake.droidblox.ui.view.views.integrations.state.IntegrationsScreenState

//private const val MOTOROLA = "motorola"
//private const val SAMSUNG_USER_AGENT =
//    "Mozilla/5.0 (Linux; Android 14; SM-S921U; Build/UP1A.231005.007) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Mobile Safari/537.363"
////const val JS_SNIPPET =
////    "javascript:(function()%7Bvar%20i%3Ddocument.createElement('iframe')%3Bdocument.body.appendChild(i)%3Balert(i.contentWindow.localStorage.token.slice(1,-1))%7D)()"
//
//@Composable
//fun LoginToDiscordButton(
//    viewModel: LoginToDiscordVM,
//    navController: NavController?
//) {
//    LaunchedEffect(true) {
//        if (!viewModel.settingsManager.token.isNullOrEmpty()) {
//            viewModel.afterLoginCallback(navController, viewModel.settingsManager.token!!)
//        } else {
//            viewModel.callbackToLoginButton = { viewModel.loginToDiscord(navController) }
//        }
//    }
//    ExtendedButton(
//        viewModel.buttonTitle,
//        viewModel.buttonDescription
//    ) {
//        viewModel.callbackToLoginButton()
//    }
//}
//
//@SuppressLint("SetJavaScriptEnabled")
//@Composable
//fun LoginToDiscordScreen(
//    viewModel: LoginToDiscordVM = hiltViewModel(),
//    navController: NavController?
//) {
//    BasicScreen(
//        name = "Login To Discord",
//        navController = navController,
//        useColumn = false,
//        navIcon = Icons.AutoMirrored.Filled.ArrowBack,
//        navIconOnClick = { navController?.navigate(Routes.INTEGRATIONS) }
//    ) {
//        AndroidView(
//            factory = { context ->
//                WebView(context).apply {
//                    layoutParams = ViewGroup.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                    )
//                    webViewClient = object : WebViewClient() {
//                        override fun shouldOverrideUrlLoading(
//                            view: WebView,
//                            url: String
//                        ): Boolean {
//                            stopLoading()
//                            if (url.endsWith("/app")) {
//                                loadUrl(JS_SNIPPET)
//                                visibility = View.GONE
//                            }
//                            return false
//                        }
//                    }
//                    settings.javaScriptEnabled = true
//                    settings.domStorageEnabled = true
//                    /*
//                        Motorola users are not able to sign into discord in a WebView:
//                        This issue is the fault of how Motorola phones (on every model) form the WebKit UA,
//                        which breaks Discord's UA parsing. This makes the browser unidentifiable.
//
//                        see https://github.com/dead8309/Kizzy/issues/345#issuecomment-2699729072
//                    */
//                    if (Build.MANUFACTURER.equals(MOTOROLA, ignoreCase = true)) {
//                        settings.userAgentString = SAMSUNG_USER_AGENT
//                    }
//                    webChromeClient = object : WebChromeClient() {
//                        override fun onJsAlert(
//                            view: WebView,
//                            url: String,
//                            message: String, // token
//                            result: JsResult
//                        ): Boolean {
//                            // final stage of logging in
//                            viewModel.afterLoginCallback(navController, message)
//                            navController?.navigate(Routes.INTEGRATIONS)
//                            visibility = View.GONE
//                            return true
//                        }
//                    }
//                    loadUrl("https://discord.com/login")
//                }
//            }
//        )
//    }
//}

//@Composable
//fun IntegrationsScreen(
//    viewModel: IntegrationsScreenVM = hiltViewModel(),
//    loginToDiscordViewModel: LoginToDiscordVM = hiltViewModel(),
//    navController: NavController? = null
//) {
////    val enableActivityTracking by viewModel.enableActivityTracking.collectAsStateWithLifecycle()
////    val showServerLocation by viewModel.showServerLocation.collectAsStateWithLifecycle()
////    val token by viewModel.token.collectAsStateWithLifecycle()
////    val showGameActivity by viewModel.showGameActivity.collectAsStateWithLifecycle()
////    val allowActivityJoining by viewModel.allowActivityJoining.collectAsStateWithLifecycle()
////    val showRobloxUser by viewModel.showRobloxUser.collectAsStateWithLifecycle()
//
//    BasicScreen("Integrations", navController) {
//        ExtendedButton(
//            "Launch Roblox",
//            "Start playing Roblox"
//        ) { viewModel.launchRoblox() }
//        SectionText("Activity tracking")
//        ExtendedSwitch(
//            "Enable activity tracking",
//            "Allow DroidBlox to detect what Roblox game you're playing.",
//            viewModel.settingsManager.showServerLocation
//        ) { viewModel.settingsManager.enableActivityTracking = it }
//        ExtendedSwitch(
//            "Query server location",
//            "When in game, you'll be able to see where your server is located. (You must have the notifications permission granted for this app)",
//            viewModel.settingsManager.showServerLocation
//        ) { viewModel.settingsManager.showServerLocation = it }
//        SectionText("Discord Rich Presence")
//        LoginToDiscordButton(loginToDiscordViewModel, navController)
//        ExtendedSwitch(
//            "Show game activity",
//            "The Roblox game you're playing will be show on your Discord profile.",
//            viewModel.settingsManager.showGameActivity
//        ) { viewModel.settingsManager.showGameActivity = it }
//        ExtendedSwitch(
//            "Allow activity joining",
//            "Allows for anybody to join the game you're currently in through your Discord profile.",
//            viewModel.settingsManager.allowActivityJoining
//        ) { viewModel.settingsManager.allowActivityJoining = it }
//        ExtendedSwitch(
//            "Show Roblox account",
//            "Shows the Roblox account you're playing with on your Discord profile.",
//            viewModel.settingsManager.showRobloxUser
//        ) { viewModel.settingsManager.showRobloxUser = it }
//    }
//}
//

@Composable
fun IntegrationsScreen(
    navController: NavController? = null,
    state: IntegrationsScreenState = IntegrationsScreenState(),
    callback: IntegrationsScreenCallbacks = IntegrationsScreenCallbacks()
) {
    BasicScreen(
        name = "Integrations",
        navController = navController
    ) {
        ExtendedButton(
            title = "Launch Roblox",
            subtitle = "Start playing Roblox",
            onClick = callback.launchRoblox
        )

        SectionText("Activity tracking")

        ExtendedSwitch(
            title = "Enable activity tracking",
            subtitle = "Allow DroidBlox to detect what Roblox game you're playing.",
            enabled = state.enableActivityTracking,
            onClick = callback.enableActivityTracking
        )
        ExtendedSwitch(
            title = "Query server location",
            subtitle = "When in game, you'll be able to see where your server is located. (You must have the notifications permission granted for this app)",
            enabled = state.queryServerLocation,
            onClick = callback.enableActivityTracking
        )

        SectionText("Discord Rich Presence")

        // TODO LoginToDiscordButton
        ExtendedSwitch(
            title = "Show game activity",
            subtitle = "The Roblox game you're playing will be show on your Discord profile.",
            enabled = state.showGameActivity,
            onClick = callback.showGameActivity
        )
        ExtendedSwitch(
            title = "Allow activity joining",
            subtitle = "Allows for anybody to join the game you're currently in through your Discord profile.",
            enabled = state.allowActivityJoining,
            onClick = callback.allowActivityJoining
        )
        ExtendedSwitch(
            title = "Show Roblox account",
            subtitle = "Shows the Roblox account you're playing with on your Discord profile.",
            enabled = state.showRobloxUser,
            onClick = callback.showRobloxUser

        )
    }
}

@Composable
fun IntegrationsScreenImpl(
    navController: NavController
) {
    val viewModel: IntegrationsScreenVM = hiltViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    IntegrationsScreen(
        navController = navController,
        state = uiState,
        callback = IntegrationsScreenCallbacks(
            launchRoblox = { viewModel.launchRoblox() },
            enableActivityTracking = {
                viewModel.setSetting(
                    key = SettingsManager.ENABLE_ACTIVITY_TRACKING,
                    value = it
                )
            },
            queryServerLocation = {
                viewModel.setSetting(
                    key = SettingsManager.QUERY_SERVER_LOCATION,
                    value = it
                )
            },
            showGameActivity = {
                viewModel.setSetting(
                    key = SettingsManager.SHOW_GAME_ACTIVITY,
                    value = it
                )
            },
            allowActivityJoining = {
                viewModel.setSetting(
                    key = SettingsManager.ALLOW_ACTIVITY_JOINING,
                    value = it
                )
            },
            showRobloxUser = {
                viewModel.setSetting(
                    key = SettingsManager.SHOW_ROBLOX_USER,
                    value = it
                )
            }
        )
    )
}

@Preview
@Composable
private fun PreviewIntegrationsScreen() {
    IntegrationsScreen()
}