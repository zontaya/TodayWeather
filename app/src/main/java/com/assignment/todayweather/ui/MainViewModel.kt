package com.assignment.todayweather.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.todayweather.data.remote.model.Coord
import com.assignment.todayweather.data.remote.model.Forecast
import com.assignment.todayweather.data.remote.model.UiResponse
import com.assignment.todayweather.domain.interactors.*
import com.assignment.todayweather.util.Constants.UNITS_IMPERIAL
import com.assignment.todayweather.util.Constants.UNITS_METRIC
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val searchCityUseCase: SearchCityUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val searchCityByLocationUseCase: SearchCityByLocationUseCase
) : ViewModel() {


    val data = MutableStateFlow<UiResponse<Forecast>>(UiResponse.Idle)
    val units = MutableStateFlow(0)

    init {
        getTemp()
    }

    fun setUnits(pos: Int) = viewModelScope.launch {
        savedStateHandle.set("unit", pos)
        units.emit(pos)
    }

    fun search(name: String, i: Int) = viewModelScope.launch {
        data.emit(UiResponse.Loading)
        val param =
            SearchCityParams(name = name, units = if (i == 0) UNITS_METRIC else UNITS_IMPERIAL)
        searchCityUseCase.execute(param)
            .flowOn(IO)
            .catch { e ->
                data.emit(UiResponse.Error(e.message))
            }
            .distinctUntilChanged()
            .collect {
                savedStateHandle.set("Key", it)

                data.emit(UiResponse.Success(it))
            }
    }

    fun getLocation(unitsPos: Int) = viewModelScope.launch(IO) {
        getLocationUseCase.execute()
            .flowOn(IO)
            .catch { e ->
            }.collect {
                searchByLocation(it, unitsPos)
            }
    }

    private suspend fun searchByLocation(
        it: Coord,
        unitsPos: Int
    ) = viewModelScope.launch {
        val param = SearchCityByLocationParams(
            location = it,
            units = if (unitsPos == 0) UNITS_METRIC else UNITS_IMPERIAL
        )
        searchCityByLocationUseCase.execute(param)
            .catch { e ->
            }.collect {
                data.emit(UiResponse.Success(it))
            }
    }

    private fun getTemp() = viewModelScope.launch {
        val temp: Forecast? = savedStateHandle.get("Key")
        temp?.let {
            data.emit(UiResponse.Success(temp))
        }
        val unit = savedStateHandle.get("unit") ?: 0
        units.emit(unit)

    }

}