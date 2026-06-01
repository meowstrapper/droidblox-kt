//// TODO: Properly use VMS
//package com.drake.droidblox.ui.view.viewmodels
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.navigation.NavController
//import com.drake.droidblox.apiservice.discord.DiscordApi
//import com.drake.droidblox.datastores.SettingsManager
//import com.drake.logger.Logger
//import com.drake.droidblox.ui.view.navigation.Routes
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.stateIn
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import javax.inject.Inject
//
//@HiltViewModel
//class LoginToDiscordVM @Inject constructor(
//    val logger: Logger,
//    val settingsManager: SettingsManager,
//    val discordApi: DiscordApi
////    val settingsManager: SettingsManager,
////    val discordApi: DiscordApi
//) : ViewModel() {
//    companion object {
//        private const val TAG = "IntegrationsScreenVM"
//    }
//    val token: StateFlow<String?> = settingsManager.get(
//        key = SettingsManager.TOKEN,
//        default = ""
//    ).stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000L),
//        initialValue = ""
//    )
//
//    var buttonTitle by mutableStateOf("Login To Discord")
//        private set
//    var buttonDescription by mutableStateOf("Login to Discord to show your game activity")
//        private set
//    var isLoggedIn by mutableStateOf(false)
//        private set
//
//    fun loginToDiscord(navController: NavController?) {
//        navController?.navigate(Routes.LOGIN_TO_DISCORD)
//    }
//
//    fun logoutOfDiscord(token: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            logger.d(TAG, "Logging out of Discord")
//            discordApi.logout(token)
//            settingsManager.set(
//                key = SettingsManager.TOKEN,
//                value = ""
//            )
//            isLoggedIn = false
//        }
//    }
//
//    // called after settingsmanager.token has been updated into a token
//    fun updateGUIAfterLogin(navController: NavController?, token: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//
//            buttonTitle = "Logout of Discord"
//            buttonDescription = "Fetching username.."
//
//            logger.d(TAG, "Fetching username for token")
//            val username = discordApi.fetchUsername(token)
//            if (username == null) {
//                logger.e(TAG, "Something went wrong with Discord's API! Stopping action")
//                return@launch
//            }
//
//            buttonDescription = "Logged in as $username"
//
//        }
//    }
////    var callbackToLoginButton = {}
////
////    fun loginToDiscord(navController: NavController?) {
////        navController?.navigate(Routes.LOGIN_TO_DISCORD)
////    }
////
////    fun afterLoginCallback(navController: NavController?, token: String) {
////        viewModelScope.launch {
////            settingsManager.token = token
////            buttonTitle = "Logout of Discord"
////            buttonDescription = "Fetching username.."
////            withContext(Dispatchers.IO) {
////                logger.d(TAG, "Fetching username for ui")
////                val username = discordApi.fetchUsername(token)
////                if (username == null) {
////                    logger.e(TAG, "Something went wrong with Discord's API! Stopping action")
////                    return@withContext
////                }
////                buttonDescription = "Logged in as $username"
////            }
////            callbackToLoginButton = { logoutOfDiscord(navController) }
////        }
////    }
////
////
////    fun logoutOfDiscord(navController: NavController?) {
////        viewModelScope.launch {
////            logger.d(TAG, "Logging out of discord")
////            withContext(Dispatchers.IO) {
////                discordApi.logout(settingsManager.token!!)
////            }
////            settingsManager.token = null
////            navController?.navigate(Routes.INTEGRATIONS)
////        }
////        callbackToLoginButton = { loginToDiscord(navController) }
////    }
//
//}