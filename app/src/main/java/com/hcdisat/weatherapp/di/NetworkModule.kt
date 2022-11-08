package com.hcdisat.weatherapp.di

import com.hcdisat.weatherapp.rest.WeatherApi
import com.hcdisat.weatherapp.rest.WeatherApiRepository
import com.hcdisat.weatherapp.rest.WeatherApiRepositoryContract
import com.hcdisat.weatherapp.viewmodel.WeatherViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

    fun providesOkHttpClient(loginInterceptor: HttpLoggingInterceptor): OkHttpClient  =
        OkHttpClient.Builder()
            .addInterceptor(loginInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()


    fun provideServiceApi(
        okHttpClient: OkHttpClient,
        moshi: Moshi): WeatherApi =
            Retrofit.Builder()
                .baseUrl(WeatherApi.BASE_URL)
//                .addCallAdapterFactory()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
                .build()
                .create(WeatherApi::class.java)

    single { providesMoshi() }
    single { provideServiceApi(get(), get()) }
    single { providesOkHttpClient(get()) }
    single { providesLoggingInterceptor() }
}

val viewModelModule = module {

    fun providesWeatherRepo(weatherApi: WeatherApi): WeatherApiRepositoryContract =
        WeatherApiRepository(weatherApi)

    single { providesWeatherRepo(get()) }
    viewModel { WeatherViewModel(get()) }
}