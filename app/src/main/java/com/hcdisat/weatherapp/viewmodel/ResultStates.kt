package com.hcdisat.weatherapp.viewmodel

import com.hcdisat.weatherapp.models.WeatherForecast

sealed class ResultStates {
    object LOADING: ResultStates()
    class SUCCESS(val results: WeatherForecast): ResultStates()
    class ERROR(val error: Throwable): ResultStates()
}
