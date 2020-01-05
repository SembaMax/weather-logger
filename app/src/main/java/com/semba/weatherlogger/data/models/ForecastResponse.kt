package com.semba.weatherlogger.data.models

import com.google.gson.annotations.SerializedName
import com.semba.weatherlogger.data.entities.ForecastEntity
import com.semba.weatherlogger.utils.extensions.toFormattedString
import java.util.*

/**
 * Created by SeMbA on 2020-01-02.
 */
data class ForecastResponse(
    @SerializedName("id") var id: Long? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("weather") var weather: ArrayList<WeatherInfo>? = null,
    @SerializedName("main") var main: MainInfo? = null,
    @SerializedName("wind") var wind: WindInfo? = null,
    @SerializedName("clouds") var clouds: CloudsInfo? = null
)
{
    fun toEntity(): ForecastEntity
    {
        return ForecastEntity(null, name, weather?.first()?.description, main?.temp,main?.feelsLike, main?.tempMin,
            main?.tempMax, main?.pressure, main?.humidity, wind?.speed ,wind?.deg ,clouds?.all,Date().toFormattedString())
    }
}