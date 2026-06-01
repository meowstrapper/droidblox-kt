package com.drake.droidblox.ui.view.views.fastflags.vm

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drake.droidblox.datastores.fastflags.EditFFlagScope
import com.drake.droidblox.datastores.fastflags.FastFlagsManager
import com.drake.droidblox.ui.view.views.fastflags.state.FFlagsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FFlagsScreenVM @Inject constructor(
    val fflagsManager: FastFlagsManager
) : ViewModel() {
    val uiState: StateFlow<FFlagsScreenState> = fflagsManager.dataStore.data.map { prefs ->
        FFlagsScreenState(
            applyFFlags = prefs[FastFlagsManager.APPLY_FAST_FLAGS] ?: true,
            forceMSAA = prefs[FastFlagsManager.ANTI_ALIASING_QUALITY] ?: "Automatic",
            renderingMode = prefs[FastFlagsManager.RENDERING_MODE] ?: "Automatic",
            textureQuality = prefs[FastFlagsManager.TEXTURE_QUALITY] ?: "Automatic",
            overrideSkyToGray = prefs[FastFlagsManager.OVERRIDE_SKY_TO_GRAY] ?: false,
            pauseVoxelizer = prefs[FastFlagsManager.PAUSE_VOXELIZER] ?: false,
            overrideQualityLevel = prefs[FastFlagsManager.OVERRIDE_QUALITY_LEVEL] ?: "",
            minGrassDistance = prefs[FastFlagsManager.MIN_GRASS_DISTANCE] ?: "",
            maxGrassDistance= prefs[FastFlagsManager.MAX_GRASS_DISTANCE] ?: "",
            lod = prefs[FastFlagsManager.LOD] ?: "",
            lod12 = prefs[FastFlagsManager.LOD12] ?: "",
            lod23 = prefs[FastFlagsManager.LOD23] ?: "",
            lod34 = prefs[FastFlagsManager.LOD34] ?: "",
            grassMovementReduced = prefs[FastFlagsManager.GRASS_MOVEMENT_REDUCED] ?: ""
        )
    }
        .distinctUntilChanged()
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = FFlagsScreenState()
    )


    fun <T> setSetting(key: Preferences.Key<T>, value: T) = viewModelScope.launch(Dispatchers.IO) {
        fflagsManager.set(key, value)
    }

    fun editFFlag(block: (EditFFlagScope.() -> Unit)) = viewModelScope.launch(Dispatchers.IO) {
        fflagsManager.editFFlag(block)
    }
}