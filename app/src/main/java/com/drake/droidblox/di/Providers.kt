package com.drake.droidblox.di

import com.drake.droidblox.apiservice.httpclient.CustomHttpClient
import com.drake.droidblox.logger.AndroidLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Providers {
    @Provides
    @Singleton
    fun provideCustomHttpClient(): HttpClient = CustomHttpClient(AndroidLogger)
}