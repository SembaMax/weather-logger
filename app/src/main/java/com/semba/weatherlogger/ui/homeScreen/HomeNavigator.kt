package com.semba.weatherlogger.ui.homeScreen

import com.semba.weatherlogger.base.BaseNavigator
import com.semba.weatherlogger.data.entities.ForecastEntity
import com.semba.weatherlogger.data.models.ForecastResponse

/**
 * Created by SeMbA on 2020-01-02.
 */
interface HomeNavigator: BaseNavigator {

    fun navigateToDetailsScreen(forecastItem: ForecastEntity)
    fun deleteForecast(forecastItem: ForecastEntity)
    fun invalidateUI()
    fun reloadRecyclerView(items: ArrayList<ForecastEntity>)
    fun updateWidget(temp: String, name: String, description: String, feelsLike: String)
}