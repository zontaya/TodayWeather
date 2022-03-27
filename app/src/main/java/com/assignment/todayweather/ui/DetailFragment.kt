package com.assignment.todayweather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.todayweather.data.remote.model.UiResponse
import com.assignment.todayweather.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    private val viewModel: DetailViewModel by inject()
    private val binding get() = _binding!!
    private lateinit var adapter: DetailAdapter
    private var units: String = "metric"
    private lateinit var lm: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        arguments?.apply {
            val data = DetailFragmentArgs.fromBundle(this)
            units = data.units
            viewModel.getDetail(data.lat.toDouble(), data.lon.toDouble(), data.units)
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lm = LinearLayoutManager(context)
        adapter = DetailAdapter()
        adapter.units=units
        binding.recyclerView.layoutManager = lm
        binding.recyclerView.adapter = adapter
        lifecycleScope.launchWhenResumed {
            viewModel.detail.collect { response ->
                when (response) {
                    is UiResponse.Error -> {
                        Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG).show()
                    }
                    UiResponse.Idle -> {

                    }
                    UiResponse.Loading -> {
                    }
                    is UiResponse.Success -> {
                        adapter.submitList(response.data.hourly)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}