package com.assignment.todayweather.domain.interactors

import com.assignment.todayweather.data.remote.model.ForecastDetail
import com.assignment.todayweather.data.remote.model.UiResponse
import com.assignment.todayweather.domain.IRepository
import com.assignment.todayweather.domain.interactors.type.UseCaseInOut
import kotlinx.coroutines.flow.Flow

class GetDailyUseCase(
    private val repository: IRepository
) : UseCaseInOut<GetDailyParams, UiResponse<ForecastDetail>> {
    override suspend fun execute(param: GetDailyParams): Flow<UiResponse<ForecastDetail>> =
        repository.getDaily(param)
}



