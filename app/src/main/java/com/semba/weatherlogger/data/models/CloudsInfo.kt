package com.semba.weatherlogger.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by SeMbA on 2020-01-02.
 */
data class CloudsInfo (
    @SerializedName("all") var all: Int? = null
)