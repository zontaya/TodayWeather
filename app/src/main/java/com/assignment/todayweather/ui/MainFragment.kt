package com.assignment.todayweather.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.assignment.todayweather.R
import com.assignment.todayweather.data.remote.model.Forecast
import com.assignment.todayweather.data.remote.model.UiResponse
import com.assignment.todayweather.databinding.FragmentMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.android.ext.android.inject


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val units = listOf("Metric : °C", "Imperial : °F")
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by inject()
    private var unitsPos = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, units)
        binding.unitsAuto?.setAdapter(adapter)
        binding.unitsAuto?.setText(units[unitsPos], false)
        binding.unitsAuto?.setOnItemClickListener { _, _, i, _ ->
            unitsPos = i
            if (binding.cityName.text.toString().trim().isNotEmpty()) {
                mainViewModel.search(binding.cityName.text.toString(), unitsPos)
            }
        }
        binding.buttonSearch.setOnClickListener {
            if (binding.cityName.text.toString().trim().isNotEmpty()) {
                mainViewModel.search(binding.cityName.text.toString().trim(), unitsPos)
            }
        }
//        binding.buttonSearch.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launchWhenResumed {

            mainViewModel.data.collect { response ->
                when (response) {
                    is UiResponse.Error -> {

                    }
                    UiResponse.Idle -> {
                        binding.cardLayout?.visibility = View.INVISIBLE
                    }
                    is UiResponse.Success -> {
                        binding.cardLayout?.visibility = View.VISIBLE
                        setCountry(response)
                        serDatetime(response)
                        setTemp(response)
                        setFeel(response)
                        setHumidity(response)
                        setIcon(response)
                    }
                    UiResponse.Loading -> {

                    }
                }
            }
        }
    }

    private fun serDatetime(response: UiResponse.Success<Forecast>) {
        val dt = convertDate(response.data.dt)
        val time = String.format(
            getString(R.string.temp_dt),
            dt.hour.toString(), dt.minute.toString()
        )
        binding.tempTime?.text = time
    }

    private fun setTemp(response: UiResponse.Success<Forecast>) {
        val timeRefreshStr = String.format(
            getString(R.string.temp),
            response.data.main.temp,
            if (unitsPos == 0) "°C" else "°F"
        )
        binding.temp.text = timeRefreshStr
    }

    private fun setCountry(response: UiResponse.Success<Forecast>) {
        val timeRefreshStr = String.format(
            getString(R.string.temp_title),
            response.data.name,
            response.data.sys.country
        )
        binding.cityCounty?.text = timeRefreshStr
    }

    private fun setFeel(response: UiResponse.Success<Forecast>) {
        val feel = String.format(
            getString(R.string.temp_feels_like),
            response.data.main.feels_like, if (unitsPos == 0) "°C" else "°F"
        )

        binding.feel?.text = feel
    }

    private fun setHumidity(response: UiResponse.Success<Forecast>) {
        val humidity = String.format(
            getString(R.string.temp_humidity),
            response.data.main.humidity, "%"
        )
        binding.humidity?.text = humidity
    }

    private fun setIcon(response: UiResponse.Success<Forecast>) {
        if (response.data.weather.isNotEmpty()) {
            val iconName = response.data.weather[0].icon
            val url = "http://openweathermap.org/img/wn/$iconName@4x.png"
            Picasso.get().load(Uri.parse(url)).into(binding.icon)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun convertDate(dateInMilliseconds: Long): LocalDateTime {
        val tz = TimeZone.currentSystemDefault()
        val currentMoment = Instant.fromEpochSeconds(dateInMilliseconds)
        return currentMoment.toLocalDateTime(tz)
    }
}