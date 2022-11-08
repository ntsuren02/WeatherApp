package com.hcdisat.weatherapp

import android.app.Application
import com.hcdisat.weatherapp.di.networkModule
import com.hcdisat.weatherapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WeatherApp)
            modules(
                listOf(networkModule, viewModelModule)
            )
        }
    }
}