package com.assignment.todayweather.data.remote.model



data class ForecastDetail (
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezoneOffset: Long,
    val current: Current,
    val minutely: List<Minutely>,
    val hourly: List<Current>
)

data class Current (
    val dt: Long,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Long,
    val humidity: Long,
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Long,
    val visibility: Long,
    val windSpeed: Double,
    val windDeg: Long,
    val windGust: Double,
    val weather: List<Weather>,
    val pop: Double? = null,
    val rain: Rain? = null
)

data class Rain (
    val the1H: Double
)

data class Minutely (
    val dt: Long,
    val precipitation: Long
)
