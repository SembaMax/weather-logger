package com.semba.weatherlogger.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by SeMbA on 2020-01-02.
 */
data class WeatherInfo (
    @SerializedName("id") var id: Long? = null,
    @SerializedName("main") var main: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("icon") var icon: String? = null
)