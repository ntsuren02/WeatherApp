package com.hcdisat.weatherapp.rest

import com.hcdisat.weatherapp.models.WeatherForecast
import retrofit2.Response

class WeatherApiRepository(
    private val weatherApi: WeatherApi
): WeatherApiRepositoryContract {

    override suspend fun getCityForecast(city: String): Response<WeatherForecast> {
        return weatherApi.getForecast(city)
    }
}

interface WeatherApiRepositoryContract {

    suspend fun getCityForecast(city: String): Response<WeatherForecast>
}