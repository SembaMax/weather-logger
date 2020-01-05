package com.semba.weatherlogger.utils

import android.util.Log

/**
 * Created by SeMbA on 2020-01-02.
 */
class Logger {

    companion object{
        const val IS_DEBUG_MODE = true
    }
    private var TAG = "WEATHER_LOGGER_TAG"

    fun<T> withTag(ofClass: Class<T>): Logger
    {
        this.TAG = ofClass.name
        return this
    }

    fun withTag(tag: String): Logger
    {
        this.TAG = tag
        return this
    }

    fun e(msg: String)
    {
        Log.e(TAG,msg)
    }

    fun i(msg: String)
    {
        Log.i(TAG,msg)
    }

    fun d(msg: String)
    {
        if (IS_DEBUG_MODE)
            Log.d(TAG,msg)
    }

    fun exception(msg: String = "", e: Exception)
    {
        Log.e(TAG,msg,e)
    }
}