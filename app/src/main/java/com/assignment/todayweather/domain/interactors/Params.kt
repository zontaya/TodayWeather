package com.assignment.todayweather.domain.interactors


class SearchCityParams(
    val name: String,
    val units: String
)

class GetDailyParams(
    val lat: Double,
    val lon: Double,
    val units: String,
    val exclude: String = "daily"
)
