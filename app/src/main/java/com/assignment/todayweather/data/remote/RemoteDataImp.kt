package com.assignment.todayweather.data.remote

import com.assignment.todayweather.data.remote.model.Forecast
import com.assignment.todayweather.domain.IRemoteData
import retrofit2.Retrofit

class RemoteDataImp(
    private val retrofit: Retrofit
) : IRemoteData {
    private val api: IRemoteData by lazy {
        retrofit.create(IRemoteData::class.java)
    }

    override suspend fun searchCityFromApi(
        name: String,
        units: String
    ) = api.searchCityFromApi(name, units)

    override suspend fun searchCityByLocationFromApi(
        lat: Double,
        lon: Double,
        units: String
    ) = api.searchCityByLocationFromApi(lat,lon, units)


    override suspend fun getDailyFromApi(
        lat: Double,
        lon: Double,
        exclude: String,
        units: String
    ) = api.getDailyFromApi(lat, lon, exclude, units)


}