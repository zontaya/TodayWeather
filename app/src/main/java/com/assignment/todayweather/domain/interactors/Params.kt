package com.assignment.todayweather.domain.interactors

import com.assignment.todayweather.data.remote.model.Coord


class SearchCityParams(
    val name: String,
    val units: String
)
class SearchCityByLocationParams(
    val location: Coord,
    val units: String
)

class GetDailyParams(
    val lat: Double,
    val lon: Double,
    val units: String,
    val exclude: String = "daily"
)
