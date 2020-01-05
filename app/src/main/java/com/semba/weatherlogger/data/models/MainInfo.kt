package com.semba.weatherlogger.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by SeMbA on 2020-01-02.
 */
data class MainInfo (
    @SerializedName("temp") var temp: Float? = null,
    @SerializedName("feels_like") var feelsLike: Float? = null,
    @SerializedName("temp_min") var tempMin: Float? = null,
    @SerializedName("temp_max") var tempMax: Float? = null,
    @SerializedName("pressure") var pressure: Float? = null,
    @SerializedName("humidity") var humidity: Float? = null
)