package com.assignment.todayweather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.todayweather.data.remote.model.Forecast
import com.assignment.todayweather.data.remote.model.UiResponse
import com.assignment.todayweather.domain.interactors.SearchCityParams
import com.assignment.todayweather.domain.interactors.SearchCityUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainViewModel(private val searchCityUseCase: SearchCityUseCase) : ViewModel() {

    val data = MutableStateFlow<UiResponse<Forecast>>(UiResponse.Idle)

    fun search(name: String) = viewModelScope.launch {
        data.emit(UiResponse.Loading)
        val param = SearchCityParams(name = name, units = "")
        searchCityUseCase.execute(param)
            .flowOn(IO)
            .distinctUntilChanged()
            .collect(data)
    }
}