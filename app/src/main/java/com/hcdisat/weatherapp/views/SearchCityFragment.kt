package com.hcdisat.weatherapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.hcdisat.weatherapp.R
import com.hcdisat.weatherapp.databinding.FragmentSearchCityBinding

const val SEARCH_TERM = "SEARCH_TERM"

class SearchCityFragment : Fragment() {

    private val binding by lazy {
        FragmentSearchCityBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.searchCity.addTextChangedListener{
            binding.btnSearchCity.isEnabled =  it?.isNotEmpty() ?: false
        }
        binding.btnSearchCity.setOnClickListener(::loadResults)

        return binding.root
    }

    private fun loadResults(view: View) {
        findNavController().navigate(
            R.id.action_searchFragment_to_forecastFragment,
            Bundle().apply {
                putString(SEARCH_TERM, binding.searchCity.text.toString())
            }
        )
    }
}