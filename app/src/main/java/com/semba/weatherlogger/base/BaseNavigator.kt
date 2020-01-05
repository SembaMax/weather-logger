package com.semba.weatherlogger.base

/**
 * Created by SeMbA on 2020-01-02.
 */
interface BaseNavigator {

    /**
     * Switch between loading mode and display data mode
     */
    fun toggleLoading(isLoading: Boolean)

    /**
     * pass a error message to the activity to display it via UI
     */
    fun showErrorMessage(message: String?)

    /**
     * pass a error message resource to the activity to display it via UI
     */
    fun showErrorMessage(messageSrc: Int)
}