package com.assignment.todayweather.domain

import com.assignment.todayweather.domain.interactors.GetDailyParams
import com.assignment.todayweather.domain.interactors.SearchCityParams
import com.assignment.todayweather.data.remote.model.Forecast
import com.assignment.todayweather.data.remote.model.ForecastDetail
import com.assignment.todayweather.data.remote.model.UiResponse
import com.assignment.todayweather.domain.interactors.SearchCityByLocationParams
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun searchCity(param: SearchCityParams): Flow<Forecast>
    suspend fun searchCityByLocation(param: SearchCityByLocationParams): Flow<Forecast>
    suspend fun getDaily(param: GetDailyParams): Flow<ForecastDetail>
}