package com.hcdisat.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcdisat.weatherapp.rest.WeatherApiRepositoryContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(
    private val weatherApiRepository: WeatherApiRepositoryContract,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _cityForecast: MutableLiveData<ResultStates> =
        MutableLiveData(ResultStates.LOADING)
    val cityForecast: LiveData<ResultStates> get() = _cityForecast

    fun getForecast(city: String) {

        viewModelScope.launch(ioDispatcher) {
            // here is the worker thread
            try {
                val response = weatherApiRepository.getCityForecast(city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        // here check for the response body if not null then we process the data
                        // live data is thread safe! so we can skip withContext
//                        _cityForecast.postValue(ResultStates.SUCCESS(it))
                        withContext(Dispatchers.Main) {
                            // Main Thread Here
                            _cityForecast.value = ResultStates.SUCCESS(it)
                        }
                    } ?: throw Exception("Response Empty")
                } else {
                    throw Exception("No Success")
                }
            } catch (e: Exception) {
                // here we handle any thrown exception
                _cityForecast.postValue(ResultStates.ERROR(e))
            }
        }
    }
}