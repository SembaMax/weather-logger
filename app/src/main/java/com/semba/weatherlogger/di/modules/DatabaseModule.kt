package com.semba.weatherlogger.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.semba.weatherlogger.data.database.AppDatabase
import com.semba.weatherlogger.di.keys.DatabaseInfoKey
import com.semba.weatherlogger.utils.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by SeMbA on 2020-01-03.
 */
@Module
class DatabaseModule {

    @Provides
    @DatabaseInfoKey
    fun provideDatabaseName(): String {
        return Constants.DB_NAME
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@DatabaseInfoKey dbName: String, context: Application): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName)
            .fallbackToDestructiveMigration().build()
    }
}