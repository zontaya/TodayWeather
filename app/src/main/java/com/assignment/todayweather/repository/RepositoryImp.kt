package com.assignment.todayweather.repository

import com.assignment.todayweather.domain.IRemoteData
import com.assignment.todayweather.domain.IRepository
import com.assignment.todayweather.domain.interactors.GetDailyParams
import com.assignment.todayweather.domain.interactors.SearchCityByLocationParams
import com.assignment.todayweather.domain.interactors.SearchCityParams
import com.assignment.todayweather.repository.model.mapper.ApiDetailUiMapper
import com.assignment.todayweather.repository.model.mapper.ApiUiMapper
import kotlinx.coroutines.flow.flow

class RepositoryImp(
    private val remote: IRemoteData,
    private val detailMapper: ApiDetailUiMapper,
    private val mapper: ApiUiMapper
) : IRepository {
    override suspend fun searchCity(param: SearchCityParams) = flow {
        val rs = remote.searchCityFromApi(param.name, param.units)
        emit(mapper.map(rs))

    }

    override suspend fun searchCityByLocation(param: SearchCityByLocationParams) = flow {
        val rs =
            remote.searchCityByLocationFromApi(param.location.lat, param.location.lon, param.units)
        emit(mapper.map(rs))
    }

    override suspend fun getDaily(param: GetDailyParams) = flow {
        val rs = remote.getDailyFromApi(param.lat, param.lon, param.exclude, param.units)
        emit(detailMapper.map(rs))
    }
}