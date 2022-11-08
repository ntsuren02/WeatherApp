package com.hcdisat.weatherapp.extensions

import com.hcdisat.weatherapp.models.Forecast
import com.hcdisat.weatherapp.models.Main
import java.text.SimpleDateFormat
import java.util.*

fun Forecast.forDate(pattern: String = "HH:mm"): String {
    val formatter = SimpleDateFormat(pattern)
    val date = Date(this.dt.toLong() * 1000)
    return formatter.format(date)
}

fun Main.fahTemp(): String =
    this.temp.kelToFah().formatFah()

fun Main.minFahTemp(): String =
    this.tempMin.kelToFah().formatFah()

fun Main.maxFahTemp(): String =
    this.tempMax.kelToFah().formatFah()

fun Double.kelToFah(): Int =
    (1.8 * (this - 273.15) + 32).toInt()

fun Int.formatFah(): String = "$this°F"

fun Double.getFahFromKel(): String =
    this.kelToFah().formatFah()