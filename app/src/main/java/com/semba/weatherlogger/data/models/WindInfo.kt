package com.semba.weatherlogger.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by SeMbA on 2020-01-02.
 */
data class WindInfo (
    @SerializedName("speed") var speed: Float? = null,
    @SerializedName("deg") var deg: Float? = null
)