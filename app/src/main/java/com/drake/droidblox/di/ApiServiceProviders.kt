package com.drake.droidblox.di

import com.drake.droidblox.apiservice.discord.DiscordApi
import com.drake.droidblox.apiservice.customHttpClient
import com.drake.droidblox.apiservice.iplocation.IpLocationApi
import com.drake.droidblox.apiservice.roblox.RobloxApi
import com.drake.droidblox.apiservice.rovalrat.RoValraApi
import com.drake.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceProviders {
    @Provides
    @Singleton
    fun provideCustomHttpClient(logger: Logger): HttpClient =
        customHttpClient(
            customLogger = logger,
            appVersion = "v1.0.0" // TODO
        )

    @Provides
    @Singleton
    fun provideDiscordApi(logger: Logger, httpClient: HttpClient): DiscordApi =
        DiscordApi(
            logger = logger,
            httpClient = httpClient
        )

    @Provides
    @Singleton
    fun provideRobloxApi(logger: Logger, httpClient: HttpClient): RobloxApi =
        RobloxApi(
            logger = logger,
            httpClient = httpClient
        )

    @Provides
    @Singleton
    fun provideRovalratApi(logger: Logger, httpClient: HttpClient): RoValraApi =
        RoValraApi(
            logger = logger,
            httpClient = httpClient
        )

    @Provides
    @Singleton
    fun provideIpLocationApi(logger: Logger, httpClient: HttpClient, roValraApi: RoValraApi): IpLocationApi =
        IpLocationApi(
            logger = logger,
            httpClient = httpClient,
            roValraApi = roValraApi
        )


}