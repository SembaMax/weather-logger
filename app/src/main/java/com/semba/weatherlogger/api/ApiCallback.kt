package com.semba.weatherlogger.api

/**
 * Created by SeMbA on 2020-01-02.
 */
interface ApiCallback {

    fun onNext(parameter: Any)
    fun onComplete()
    fun onError(message: String)
}