package com.semba.weatherlogger.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.semba.weatherlogger.data.entities.ForecastEntity

/**
 * Created by SeMbA on 2020-01-03.
 */

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forecast: ForecastEntity): Long

    @Delete
    fun deleteItem(item: ForecastEntity): Int

    @Query("DELETE FROM forecasts")
    fun deleteAll()

    @Query("SELECT * FROM forecasts")
    fun loadAll() : LiveData<List<ForecastEntity>>
}