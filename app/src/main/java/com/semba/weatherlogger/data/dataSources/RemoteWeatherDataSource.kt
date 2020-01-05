package com.semba.weatherlogger.data.dataSources

import com.semba.weatherlogger.api.ApiService
import com.semba.weatherlogger.data.models.ForecastResponse
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by SeMbA on 2020-01-02.
 */
class RemoteWeatherDataSource @Inject constructor(private val apiService: ApiService): RemoteDataSource {

    override fun getLatestForecast(latitude: Double, longitude: Double): Observable<ForecastResponse> {
        return apiService.getWeatherInfo(latitude, longitude)
    }
}