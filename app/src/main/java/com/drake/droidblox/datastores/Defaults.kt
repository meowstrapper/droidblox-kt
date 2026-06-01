package com.drake.droidblox.datastores

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.drake.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

open class Defaults(
    private val dataStore: DataStore<Preferences>
) {
    fun <T> get(key: Preferences.Key<T>, default: T): Flow<T> =
        dataStore.data.map { it[key] ?: default }

    suspend fun <T> getCurrentValue(key: Preferences.Key<T>, default: T): T =
        dataStore.data.first()[key] ?: default

    suspend fun <T> set(key: Preferences.Key<T>, value: T) =
        dataStore.edit { it[key] = value }

    suspend fun edit(transform: suspend (MutablePreferences) -> Unit) =
        dataStore.edit(transform)

}