package com.hcdisat.weatherapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hcdisat.weatherapp.BuildConfig
import com.hcdisat.weatherapp.R
import com.hcdisat.weatherapp.adatpters.WeatherAdapter
import com.hcdisat.weatherapp.databinding.FragmentForecastBinding
import com.hcdisat.weatherapp.extensions.fahTemp
import com.hcdisat.weatherapp.extensions.maxFahTemp
import com.hcdisat.weatherapp.extensions.minFahTemp
import com.hcdisat.weatherapp.models.Forecast
import com.hcdisat.weatherapp.viewmodel.ResultStates

const val FORECAST_KEY = "FORECAST_KEY"
const val CITY_KEY = "CITY_KEY"

class ForecastFragment : BaseFragment() {

    private val binding by lazy {
        FragmentForecastBinding.inflate(layoutInflater)
    }

    private val weatherAdapter by lazy {
        WeatherAdapter(onHolderClicked = ::onItemClicked)
    }

    private var cityToSearch: String = "Marietta"

    private fun onItemClicked(forecast: Forecast) {
        findNavController().navigate(
            R.id.action_forecastFragment_to_detailsFragment,
            Bundle().apply {
                putParcelable(FORECAST_KEY, forecast)
                putString(CITY_KEY, cityToSearch)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            cityToSearch = it?.getString(SEARCH_TERM) ?: cityToSearch
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        weatherViewModel.cityForecast.observe(viewLifecycleOwner, ::handleState)
        weatherViewModel.getForecast(cityToSearch)

        binding.cityContent.forecastList.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = weatherAdapter
        }

        return binding.root
    }

    private fun handleState(resultState: ResultStates) {
        when (resultState) {
            is ResultStates.LOADING -> {
                Toast.makeText(
                    requireContext(),
                    "LOADING.....",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is ResultStates.SUCCESS -> bind(resultState)

            is ResultStates.ERROR -> {
                if (BuildConfig.BUILD_TYPE == "debug")
                    Log.e("TAG", "handleState: ${resultState.error.localizedMessage}")

                binding.cityContent.contentGroup.visibility = View.GONE
                binding.cityNoContent.notFoundGroup.visibility = View.VISIBLE
            }
        }
    }

    private fun bind(resultStates: ResultStates.SUCCESS) {
        binding.cityNoContent.notFoundGroup.visibility = View.GONE

        binding.cityContent.apply {
            forecastDescription.text = resultStates
                .results.forecast[0].weather[0].description

            contentGroup.visibility = View.VISIBLE
            forecastTemp.text = resultStates.results.forecast[0].main.fahTemp()
            forecastCity.text = resultStates.results.city.name
            forecastTempMax.text = resultStates.results.forecast[0].main.maxFahTemp()
            forecastTempMin.text = resultStates.results.forecast[0].main.minFahTemp()

            weatherAdapter.setForecast(resultStates.results.forecast)

            forecastLoading.visibility = View.GONE
            forecastMainContainer.visibility = View.VISIBLE
        }
    }
}