package com.assignment.todayweather.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.todayweather.data.remote.model.Forecast
import com.assignment.todayweather.data.remote.model.UiResponse
import com.assignment.todayweather.domain.interactors.SearchCityParams
import com.assignment.todayweather.domain.interactors.SearchCityUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val searchCityUseCase: SearchCityUseCase
) : ViewModel() {
    companion object {
        const val UNITS_METRIC = "metric"
        const val UNITS_IMPERIAL = "imperial"
    }

    val data = MutableStateFlow<UiResponse<Forecast>>(UiResponse.Idle)
    var unit: Int = 0

    init {
        getTemp()
    }

    fun search(name: String, i: Int) = viewModelScope.launch {

        data.emit(UiResponse.Loading)
        val param =
            SearchCityParams(name = name, units = if (i == 0) UNITS_METRIC else UNITS_IMPERIAL)
        searchCityUseCase.execute(param)
            .flowOn(IO).catch { e ->
                data.emit(UiResponse.Error(e.message))
            }
            .distinctUntilChanged()
            .collect {
                savedStateHandle.set("Key", it)
                savedStateHandle.set("unit", i)
                data.emit(UiResponse.Success(it))
            }
    }

    private fun getTemp() = viewModelScope.launch {
        val temp: Forecast? = savedStateHandle.get("Key")
        unit = savedStateHandle.get("unit") ?: 0
        if (temp != null) {
            data.emit(UiResponse.Success(temp))
        }
    }

}