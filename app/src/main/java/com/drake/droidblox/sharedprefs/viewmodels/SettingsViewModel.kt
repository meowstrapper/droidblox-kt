package com.drake.droidblox.sharedprefs.viewmodels

import androidx.lifecycle.ViewModel
import com.drake.droidblox.sharedprefs.SettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
  private val settingsManager: SettingsManager
): ViewModel() {
}