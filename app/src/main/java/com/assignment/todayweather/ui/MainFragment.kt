package com.assignment.todayweather.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.assignment.todayweather.R
import com.assignment.todayweather.data.remote.model.Forecast
import com.assignment.todayweather.data.remote.model.UiResponse
import com.assignment.todayweather.databinding.FragmentMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
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
        binding.cityName.setText("bangkok")
        binding.unitsAuto.setOnItemClickListener { _, _, i, _ ->
            unitsPos = i
            if (binding.cityName.text.toString().trim().isNotEmpty()) {
                mainViewModel.search(binding.cityName.text.toString(), unitsPos)
            }
        }
        binding.buttonSearch.setOnClickListener {
            if (binding.cityName.text.toString().trim().isNotEmpty()) {
                mainViewModel.search(binding.cityName.text.toString().trim(), unitsPos)
                val inputManager =  requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(view.windowToken, 0);
            }
        }
        binding.cardLayout.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.data.map {
                    it as UiResponse.Success
                    it.data.coord
                }.catch { e ->
                    println(e.message)
                }.collectLatest {
                    findNavController().navigate(
                        MainFragmentDirections.actionMainToDetail(
                            it.lat.toString(),
                            it.lon.toString(),
                            if (unitsPos == 0) "metric" else "imperial"
                        )
                    )
                }
            }


        }

        lifecycleScope.launchWhenStarted{

            mainViewModel.data.collect { response ->
                when (response) {
                    is UiResponse.Error -> {
                        Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG).show()
                    }
                    UiResponse.Idle -> {
                        binding.cardLayout.visibility = View.INVISIBLE
                    }
                    is UiResponse.Success -> {

                        response.data.coord.lat
                        response.data.coord.lon

                        binding.cardLayout.visibility = View.VISIBLE
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

    override fun onResume() {
        super.onResume()
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, units)
        unitsPos = mainViewModel.unit
        binding.unitsAuto.setText(units[unitsPos], false)
        binding.unitsAuto.setAdapter(adapter)
    }

    private fun serDatetime(response: UiResponse.Success<Forecast>) {
        val dt = convertDate(response.data.dt)
        binding.tempTime.text = dt
    }

    private fun setTemp(response: UiResponse.Success<Forecast>) {
        val temp = String.format(
            getString(R.string.temp),
            response.data.main.temp,
            if (unitsPos == 0) "°C" else "°F"
        )
        binding.temp.text = temp
    }

    private fun setCountry(response: UiResponse.Success<Forecast>) {
        val timeRefreshStr = String.format(
            getString(R.string.temp_title),
            response.data.name,
            response.data.sys.country
        )
        binding.cityCounty.text = timeRefreshStr
    }

    private fun setFeel(response: UiResponse.Success<Forecast>) {
        val feel = String.format(
            getString(R.string.temp_feels_like),
            response.data.main.feels_like, if (unitsPos == 0) "°C" else "°F"
        )

        binding.feel.text = feel
    }

    private fun setHumidity(response: UiResponse.Success<Forecast>) {
        val humidity = String.format(
            getString(R.string.temp_humidity),
            response.data.main.humidity, "%"
        )
        binding.humidity.text = humidity
    }

    private fun setIcon(response: UiResponse.Success<Forecast>) {
        if (response.data.weather.isNotEmpty()) {
            val iconName = response.data.weather[0].icon
            Picasso.get().load(iconName).into(binding.icon)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun convertDate(dateInMilliseconds: Long): String {
        val tz = TimeZone.currentSystemDefault()
        val currentMoment = Instant.fromEpochSeconds(dateInMilliseconds)
        val dt = currentMoment.toLocalDateTime(tz)
        var minute = "${dt.minute}"

        if (dt.minute < 10) {
            minute = "0${dt.minute}"
        }
        return String.format(
            getString(R.string.temp_dt),
            dt.hour, minute
        )
    }
}