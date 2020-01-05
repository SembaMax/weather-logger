package com.semba.weatherlogger.data.repositories

import androidx.lifecycle.LiveData
import com.semba.weatherlogger.data.dataSources.OfflineWeatherDataSource
import com.semba.weatherlogger.data.dataSources.RemoteWeatherDataSource
import com.semba.weatherlogger.data.entities.ForecastEntity
import com.semba.weatherlogger.data.models.ForecastResponse
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by SeMbA on 2020-01-02.
 */
class WeatherRepository @Inject constructor(private val remoteDataSource: RemoteWeatherDataSource, private val offlineWeatherDataSource: OfflineWeatherDataSource): IRepository {

    companion object {
        const val CALL_REPEAT_RATE = 1L
    }

    override fun callGetLatestCurrenciesApi(latitude: Double, longitude: Double): Observable<ForecastResponse>
    {
        return remoteDataSource.getLatestForecast(latitude, longitude).repeatWhen { it.delay(CALL_REPEAT_RATE, TimeUnit.MINUTES) }
    }

    override fun loadForecastCache(): LiveData<List<ForecastEntity>> {
        return offlineWeatherDataSource.getForecastItems()
    }

    override fun insertForecast(forecast: ForecastEntity): Observable<Long> {
        return offlineWeatherDataSource.insertForecastItem(forecast)
    }

    override fun deleteForecast(forecast: ForecastEntity): Observable<Int> {
        return offlineWeatherDataSource.deleteForecastItem(forecast)
    }

    override fun deleteAllForecastCache() {
        offlineWeatherDataSource.deleteAllForecastItems()
    }
}