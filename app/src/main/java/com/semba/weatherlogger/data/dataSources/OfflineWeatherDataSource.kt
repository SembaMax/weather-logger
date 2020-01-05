package com.semba.weatherlogger.data.dataSources

import androidx.lifecycle.LiveData
import com.semba.weatherlogger.data.database.AppDatabase
import com.semba.weatherlogger.data.entities.ForecastEntity
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by SeMbA on 2020-01-03.
 */
class OfflineWeatherDataSource @Inject constructor(private val appDatabase: AppDatabase): OfflineDataSource {

    override fun getForecastItems(): LiveData<List<ForecastEntity>> {
        return appDatabase.forecastDao().loadAll()
    }

    override fun insertForecastItem(item: ForecastEntity): Observable<Long> {
        return Observable.fromCallable {
            appDatabase.forecastDao().insert(item)
        }
    }

    override fun deleteForecastItem(item: ForecastEntity): Observable<Int> {
        return Observable.fromCallable {
            appDatabase.forecastDao().deleteItem(item)
        }
    }

    override fun deleteAllForecastItems() {
        appDatabase.forecastDao().deleteAll()
    }
}