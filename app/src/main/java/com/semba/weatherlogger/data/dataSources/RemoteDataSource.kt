package com.semba.weatherlogger.data.dataSources

import com.semba.weatherlogger.data.models.ForecastResponse
import io.reactivex.Observable

/**
 * Created by SeMbA on 2020-01-03.
 */
interface RemoteDataSource {

    fun getLatestForecast(latitude: Double, longitude: Double): Observable<ForecastResponse>
}