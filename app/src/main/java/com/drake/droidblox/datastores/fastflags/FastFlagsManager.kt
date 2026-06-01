package com.drake.droidblox.datastores.fastflags

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.drake.droidblox.datastores.Defaults
import com.drake.droidblox.datastores.fastflags.EditFFlagScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FastFlagsManager @Inject constructor(
    val dataStore: DataStore<Preferences>
) : Defaults(dataStore) {
    companion object {
        val FAST_FLAGS                          = stringPreferencesKey("fflags")
        val APPLY_FAST_FLAGS                    = booleanPreferencesKey("applyFFlags")

        // ui values (too many boilerplate code here, can this be optimized?)
        val ANTI_ALIASING_QUALITY               = stringPreferencesKey("antiAliasingQuality")
        val RENDERING_MODE                      = stringPreferencesKey("renderingMode")
        val TEXTURE_QUALITY                     = stringPreferencesKey("textureQuality")
        val OVERRIDE_SKY_TO_GRAY                = booleanPreferencesKey("overrideSkyToGray")
        val PAUSE_VOXELIZER                     = booleanPreferencesKey("pauseVoxelizer")
        val OVERRIDE_QUALITY_LEVEL              = stringPreferencesKey("overrideQualityLevel")
        val MIN_GRASS_DISTANCE                  = stringPreferencesKey("minGrassDistance")
        val MAX_GRASS_DISTANCE                  = stringPreferencesKey("maxGrassDistance")

        val LOD                                 = stringPreferencesKey("lod")
        val LOD12                               = stringPreferencesKey("lod12")
        val LOD23                               = stringPreferencesKey("lod23")
        val LOD34                               = stringPreferencesKey("lod34")

        val GRASS_MOVEMENT_REDUCED              = stringPreferencesKey("grassMovementReduced")

    }

    private val json = Json { ignoreUnknownKeys = true }
    
    suspend fun getFFlags(): String =
        getCurrentValue(
            key = FAST_FLAGS,
            default = "{}"
        )

    suspend fun editFFlag(block: (EditFFlagScope.() -> Unit)) =
        dataStore.edit {
            val currentFFlags: MutableMap<String, String> = json.decodeFromString(it[FAST_FLAGS] ?: "{}")
            val scope = EditFFlagScope(fflagsToEdit = currentFFlags)
            scope.block()
            currentFFlags.putAll(scope.fflagsToEdit)
            it[FAST_FLAGS] = json.encodeToString(currentFFlags)
        }
}