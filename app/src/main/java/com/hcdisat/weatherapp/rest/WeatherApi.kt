package com.hcdisat.weatherapp.rest

import com.hcdisat.weatherapp.models.WeatherForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(FORECAST_PATH)
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("appId") appId: String = API_KEY
    ): Response<WeatherForecast>

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
        private const val FORECAST_PATH = "data/2.5/forecast"

        private const val API_KEY = "b5b5842ff9654788357181f2906a68db"
    }
}