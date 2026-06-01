package com.drake.droidblox.di

import android.content.Context
import com.drake.droidblox.datastores.ModsManager
import com.drake.droidblox.texturemods.TextureMods
import com.drake.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TextureModsProvider {
    @Provides
    @Singleton
    fun provideTextureMods(logger: Logger, httpClient: HttpClient, modsManager: ModsManager, @ApplicationContext context: Context): TextureMods =
        TextureMods(
            logger = logger,
            httpClient = httpClient,
            modsManager = modsManager,
            context = context
        )
}