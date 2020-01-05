package com.semba.weatherlogger.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by SeMbA on 2020-01-03.
 */

fun Date?.toFormattedString(): String
{
    if (this == null)
        return ""

    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    return formatter.format(this)
}