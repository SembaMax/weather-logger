package com.semba.weatherlogger.di.modules

import com.semba.weatherlogger.api.ApiService
import com.semba.weatherlogger.data.dataSources.OfflineWeatherDataSource
import com.semba.weatherlogger.data.dataSources.RemoteWeatherDataSource
import com.semba.weatherlogger.data.database.AppDatabase
import com.semba.weatherlogger.data.repositories.WeatherRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by SeMbA on 2020-01-02.
 */

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideRemoteWeatherDataSource(apiService: ApiService): RemoteWeatherDataSource
    {
        return RemoteWeatherDataSource(apiService)
    }

    @Singleton
    @Provides
    fun provideOfflineWeatherDataSource(appDatabase: AppDatabase): OfflineWeatherDataSource
    {
        return OfflineWeatherDataSource(appDatabase)
    }

    @Singleton
    @Provides
    fun provideGithubRepository(remoteDataSource: RemoteWeatherDataSource, offlineWeatherDataSource: OfflineWeatherDataSource): WeatherRepository
    {
        return WeatherRepository(remoteDataSource, offlineWeatherDataSource)
    }
}