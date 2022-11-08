package com.hcdisat.weatherapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hcdisat.weatherapp.databinding.FragmentForecastDetailsBinding
import com.hcdisat.weatherapp.extensions.fahTemp
import com.hcdisat.weatherapp.extensions.forDate
import com.hcdisat.weatherapp.extensions.maxFahTemp
import com.hcdisat.weatherapp.extensions.minFahTemp
import com.hcdisat.weatherapp.models.Forecast

class ForecastDetailsFragment : Fragment() {

    private val binding by lazy {
        FragmentForecastDetailsBinding.inflate(layoutInflater)
    }

    private var forecast: Forecast? = null
    private var cityName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            forecast = it?.getParcelable(FORECAST_KEY)
            cityName = it?.getString(CITY_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bind()

        return binding.root
    }

    private fun bind() {
        forecast?.let {
            binding.city.text = String.format("@$cityName")
            binding.date.text = it.forDate("MM/dd H:m:s")
            binding.description.text = it.weather[0].description
            binding.temp.text = it.main.fahTemp()
            binding.tempMax.text = String.format("H:${it.main.maxFahTemp()}")
            binding.tempMin.text = String.format("L:${it.main.minFahTemp()}")
        }
    }
}