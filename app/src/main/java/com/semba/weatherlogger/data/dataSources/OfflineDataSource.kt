package com.semba.weatherlogger.data.dataSources

import androidx.lifecycle.LiveData
import com.semba.weatherlogger.data.entities.ForecastEntity
import io.reactivex.Observable

/**
 * Created by SeMbA on 2020-01-03.
 */
interface OfflineDataSource {

    fun getForecastItems() : LiveData<List<ForecastEntity>>
    fun insertForecastItem(item: ForecastEntity): Observable<Long>
    fun deleteForecastItem(item: ForecastEntity): Observable<Int>
    fun deleteAllForecastItems()
}