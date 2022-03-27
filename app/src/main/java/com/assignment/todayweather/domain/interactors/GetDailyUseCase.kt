package com.assignment.todayweather.domain.interactors

import com.assignment.todayweather.data.remote.model.ForecastDetail
import com.assignment.todayweather.data.remote.model.UiResponse
import com.assignment.todayweather.domain.IRepository
import com.assignment.todayweather.domain.interactors.type.UseCaseInOut
import kotlinx.coroutines.flow.Flow

class GetDailyUseCase(
    private val repository: IRepository
) : UseCaseInOut<GetDailyParams, ForecastDetail> {
    override suspend fun execute(param: GetDailyParams): Flow<ForecastDetail> =
        repository.getDaily(param)
}



