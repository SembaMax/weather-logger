package com.semba.weatherlogger.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import javax.inject.Inject

/**
 * Created by SeMbA on 2020-01-04.
 */
class SharedPreferencesManager @Inject constructor(appContext: Context) {

    companion object{
        const val NAME_PREF = "NAME_PREF"
        const val TEMP_PREF = "TEMP_PREF"
        const val FEELS_LIKE_PREF = "FEELS_PREF"
        const val DESCRIPTION_PREF = "DESCRIPTION_PREF"
    }

    private val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun saveString(value: String, key: String)
    {
        sharedPref.edit()?.putString(key, value)?.commit()
    }

    fun fetchString(key: String): String
    {
        return sharedPref.getString(key, "") ?: ""
    }

    fun removeString(key: String)
    {
        sharedPref.edit()?.remove(key)?.commit()
    }
}