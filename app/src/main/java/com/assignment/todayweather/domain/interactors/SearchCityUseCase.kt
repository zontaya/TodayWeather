package com.assignment.todayweather.domain.interactors

import com.assignment.todayweather.domain.interactors.type.UseCaseInOut
import com.assignment.todayweather.domain.IRepository
import com.assignment.todayweather.data.remote.model.Forecast
import com.assignment.todayweather.data.remote.model.UiResponse
import kotlinx.coroutines.flow.Flow

class SearchCityUseCase(
    private val repository: IRepository
) : UseCaseInOut<SearchCityParams, UiResponse<Forecast>> {
    override suspend fun execute(param: SearchCityParams): Flow<UiResponse<Forecast>> =
        repository.searchCity(param)
}



