package com.semba.weatherlogger.data.repositories

import androidx.lifecycle.LiveData
import com.semba.weatherlogger.data.entities.ForecastEntity
import com.semba.weatherlogger.data.models.ForecastResponse
import io.reactivex.Observable

/**
 * Created by SeMbA on 2020-01-02.
 */
interface IRepository {

    fun callGetLatestCurrenciesApi(latitude: Double, longitude: Double): Observable<ForecastResponse>

    fun loadForecastCache() : LiveData<List<ForecastEntity>>
    fun insertForecast(forecast: ForecastEntity): Observable<Long>
    fun deleteForecast(forecast: ForecastEntity): Observable<Int>
    fun deleteAllForecastCache()
}