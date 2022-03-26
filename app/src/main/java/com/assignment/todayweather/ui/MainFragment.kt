package com.assignment.todayweather.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.assignment.todayweather.data.remote.model.Forecast
import com.assignment.todayweather.data.remote.model.UiResponse
import com.assignment.todayweather.databinding.FragmentFirstBinding
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSearch.setOnClickListener {
            if (binding.cityName.text.toString().trim().isNotEmpty()) {
                mainViewModel.search(binding.cityName.text.toString().trim())
            }
        }
//        binding.buttonSearch.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.data.collect { response ->
                when (response) {
                    is UiResponse.Error -> {}
                    UiResponse.Idle -> {}
                    is UiResponse.Success -> {
                        binding.temp.text = response.data.main.temp.toString()
                        binding.des.text = response.data.weather[0].description
                        binding.name.text = response.data.name
                        setIcon(response)
                    }
                    UiResponse.Loading -> {

                    }
                }
            }
        }
    }

    private fun setIcon(response: UiResponse.Success<Forecast>) {
        if (response.data.weather.isNotEmpty()) {
            val iconName = response.data.weather[0].icon
            val url = "http://openweathermap.org/img/wn/$iconName@2x.png"
            Picasso.get().load(Uri.parse(url)).into(binding.icon)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}