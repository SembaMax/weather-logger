package com.semba.weatherlogger.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.semba.weatherlogger.data.database.dao.ForecastDao
import com.semba.weatherlogger.data.entities.ForecastEntity
import com.semba.weatherlogger.data.models.ForecastResponse

/**
 * Created by SeMbA on 2020-01-03.
 */
@Database(entities = [ForecastEntity::class], version = 4)
abstract class AppDatabase: RoomDatabase() {

    abstract fun forecastDao(): ForecastDao
}