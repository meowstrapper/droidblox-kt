package com.drake.droidblox.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.drake.droidblox.datastores.fastflags.FastFlagsManager
import com.drake.droidblox.datastores.ModsManager
import com.drake.droidblox.datastores.SettingsManager
import com.drake.droidblox.datastores.playsessions.PlaySessionsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreProviders {
    private val Context.modsManagerDataStore by preferencesDataStore(name = "modsManager")

    @Provides
    @Singleton
    fun provideModsManager(@ApplicationContext context: Context): ModsManager =
        ModsManager(
            dataStore = context.modsManagerDataStore
        )

    private val Context.fastFlagsManagerDataStore by preferencesDataStore(name = "fastFlagsManager")

    @Provides
    @Singleton
    fun provideFastFlagsManager(@ApplicationContext context: Context): FastFlagsManager =
        FastFlagsManager(
            dataStore = context.fastFlagsManagerDataStore
        )

    private val Context.settingsManagerDataStore by preferencesDataStore(name = "settingsManager")

    @Provides
    @Singleton
    fun provideSettingsManager(@ApplicationContext context: Context): SettingsManager =
        SettingsManager(
            dataStore = context.settingsManagerDataStore
        )

    private val Context.playSessionsManagerDataStore by preferencesDataStore(name = "playSessionsManager")

    @Provides
    @Singleton
    fun providePlaySessionsManager(@ApplicationContext context: Context): PlaySessionsManager =
        PlaySessionsManager(
            dataStore = context.playSessionsManagerDataStore
        )
}