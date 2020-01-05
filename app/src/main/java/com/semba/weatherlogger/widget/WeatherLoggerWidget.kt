package com.semba.weatherlogger.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.semba.weatherlogger.R
import com.semba.weatherlogger.ui.mainNavScreen.MainNavActivity
import com.semba.weatherlogger.utils.SharedPreferencesManager

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [WeatherLoggerWidgetConfigureActivity]
 */
class WeatherLoggerWidget : AppWidgetProvider() {

    companion object{
        const val TAG = "WEATHER_WIDGET_TAG"
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        //val widgetText = loadTitlePref(context, appWidgetId)
        // Construct the RemoteViews object

        val preferences = SharedPreferencesManager(context)
        val temp: String = preferences.fetchString(SharedPreferencesManager.TEMP_PREF)
        val name: String = preferences.fetchString(SharedPreferencesManager.NAME_PREF)
        val feelsLike: String = preferences.fetchString(SharedPreferencesManager.FEELS_LIKE_PREF)
        val description: String = preferences.fetchString(SharedPreferencesManager.DESCRIPTION_PREF)

        Log.d(TAG, "updating temp $temp")
        val views = RemoteViews(context.packageName, R.layout.weather_logger_widget)
        if (name != "")
            views.setTextViewText(R.id.name_textView, name)
        views.setTextViewText(R.id.temp_textView, temp)
        views.setTextViewText(R.id.description_textView, description)
        views.setTextViewText(R.id.feels_like_textView, feelsLike)

        val intent = Intent(context, MainNavActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        views.setOnClickPendingIntent(R.id.save_btn, pendingIntent)
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}