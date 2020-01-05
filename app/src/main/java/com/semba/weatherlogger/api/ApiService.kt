package com.semba.weatherlogger.api

import com.semba.weatherlogger.data.models.ForecastResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by SeMbA on 2020-01-02.
 */
interface ApiService {

    @GET(Config.weather)
    fun getWeatherInfo(@Query("lat") latitude: Double, @Query("lon") longitude: Double): Observable<ForecastResponse>
}