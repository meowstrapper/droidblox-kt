package com.drake.droidblox.di

import com.drake.logger.AndroidLogger
import com.drake.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoggerProvider {
    @Provides
    @Singleton
    fun provideLogger(): Logger = AndroidLogger
}