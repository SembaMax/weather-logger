package com.semba.weatherlogger.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by SeMbA on 2020-01-03.
 */

@Parcelize
@Entity(tableName = "forecasts")
data class ForecastEntity (

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "temp")
    var temp: Float? = null,

    @ColumnInfo(name = "feels_like")
    var feelsLike: Float? = null,

    @ColumnInfo(name = "temp_min")
    var tempMin: Float? = null,

    @ColumnInfo(name = "temp_max")
    var tempMax: Float? = null,

    @ColumnInfo(name = "pressure")
    var pressure: Float? = null,

    @ColumnInfo(name = "humidity")
    var humidity: Float? = null,

    @ColumnInfo(name = "wind_speed")
    var windSpeed: Float? = null,

    @ColumnInfo(name = "wind_degree")
    var windDegree: Float? = null,

    @ColumnInfo(name = "clouds")
    var clouds: Int? = null,

    @ColumnInfo(name = "date")
    var date: String? = null

): Parcelable