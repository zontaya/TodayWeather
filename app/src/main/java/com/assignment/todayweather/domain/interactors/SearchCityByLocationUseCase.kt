package com.assignment.todayweather.domain.interactors

import com.assignment.todayweather.data.remote.model.Forecast
import com.assignment.todayweather.domain.IRepository
import com.assignment.todayweather.domain.interactors.type.UseCaseInOut
import kotlinx.coroutines.flow.Flow

class SearchCityByLocationUseCase(
    private val repository: IRepository
) : UseCaseInOut<SearchCityByLocationParams, Forecast> {
    override suspend fun execute(param: SearchCityByLocationParams): Flow<Forecast> =
        repository.searchCityByLocation(param)
}



