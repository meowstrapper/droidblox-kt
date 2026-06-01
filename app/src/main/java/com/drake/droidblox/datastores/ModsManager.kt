package com.drake.droidblox.datastores

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModsManager @Inject constructor(
    val dataStore: DataStore<Preferences>
) : Defaults(dataStore) {
    companion object {
        val APPLY_MODS                              = booleanPreferencesKey("applyMods")
        val TEXTURE_MODS_ALREADY_CONFIGURED         = booleanPreferencesKey("textureModsAlreadyConfigured")

        val MOUSE_CURSOR                            = stringPreferencesKey("mouseCursor")
        val USE_OLD_AVATAR_EDITOR                   = booleanPreferencesKey("useOldAvatarEditor")
        val EMULATE_OLD_CHARACTER_SOUNDS            = booleanPreferencesKey("emulateOldCharacterSounds")
        val PREFERRED_EMOJI_TYPE                    = stringPreferencesKey("preferredEmojiType")
    }

}