package com.assignment.todayweather.repository


import com.assignment.todayweather.domain.IRemoteData
import com.assignment.todayweather.domain.IRepository
import com.assignment.todayweather.domain.interactors.GetDailyParams
import com.assignment.todayweather.domain.interactors.SearchCityParams
import com.assignment.todayweather.data.remote.model.UiResponse
import com.assignment.todayweather.repository.model.mapper.ApiUiMapper
import kotlinx.coroutines.flow.flow

class RepositoryImp(
    private val remote: IRemoteData,
    private val detailMapper: ApiUiMapper
) : IRepository {
    override suspend fun searchCity(param: SearchCityParams) =
        flow {
            try {
                val rs = remote.searchCityFromApi(param.name, param.units)
                emit(UiResponse.Success(rs))
            } catch (e: Exception) {
                emit(UiResponse.Error(e.message))
            }
        }

    override suspend fun getDaily(param: GetDailyParams) = flow {
        try {
            val rs = remote.getDailyFromApi(param.lat, param.lon, param.exclude, param.units)
            emit(UiResponse.Success(rs))
        } catch (e: Exception) {
            emit(UiResponse.Error(e.message))
        }
    }
}