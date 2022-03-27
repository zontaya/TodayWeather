package com.assignment.todayweather.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.todayweather.data.remote.model.ForecastDetail
import com.assignment.todayweather.data.remote.model.UiResponse
import com.assignment.todayweather.domain.interactors.GetDailyParams
import com.assignment.todayweather.domain.interactors.GetDailyUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class DetailViewModel(

    private val getDailyUseCase: GetDailyUseCase,
    private val savedStateHandle: SavedStateHandle

) : ViewModel() {

    val detail = MutableStateFlow<UiResponse<ForecastDetail>>(UiResponse.Idle)

    init {
        getTemp()
    }

    fun getDetail(lat: Double, lon: Double, units: String) = viewModelScope.launch {
        detail.emit(UiResponse.Loading)
        val param = GetDailyParams(lat, lon, units)
        getDailyUseCase.execute(param)
            .flowOn(Dispatchers.IO).catch { e ->
                detail.emit(UiResponse.Error(e.message))
            }
            .collect {
                savedStateHandle.set("detail", it)
                detail.emit(UiResponse.Success(it))
            }
    }

    private fun getTemp() = viewModelScope.launch {
        val temp: ForecastDetail? = savedStateHandle.get("detail")
        if (temp != null) {
            detail.emit(UiResponse.Success(temp))
        }
    }
}