package com.semba.weatherlogger.di.modules

import android.app.Application
import com.semba.weatherlogger.utils.Logger
import com.semba.weatherlogger.utils.SharedPreferencesManager
import com.semba.weatherlogger.utils.rx.AppSchedulerProvider
import com.semba.weatherlogger.utils.rx.ISchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by SeMbA on 2020-01-02.
 */
@Module
class UtilsModule {

    @Singleton
    @Provides
    fun provideRxScheduler() : ISchedulerProvider
    {
        return AppSchedulerProvider()
    }

    @Singleton
    @Provides
    fun provideSharedPreferencesManager(context: Application) : SharedPreferencesManager
    {
        return SharedPreferencesManager(context)
    }

    @Singleton
    @Provides
    fun provideLogger() : Logger
    {
        return Logger()
    }
}