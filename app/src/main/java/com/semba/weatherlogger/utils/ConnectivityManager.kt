package com.semba.weatherlogger.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by SeMbA on 2020-01-02.
 */
object ConnectivityManager {

    fun isConnected(context: Context): Boolean
    {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager?.activeNetworkInfo
        return activeNetworkInfo?.isConnectedOrConnecting ?: false
    }
}