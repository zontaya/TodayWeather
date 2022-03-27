package com.assignment.todayweather.data.remote.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Forecast (
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Long,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long
) : Parcelable


@Parcelize
data class Clouds(
    val all: Long
) : Parcelable
@Parcelize
data class Coord(
    val lon: Double,
    val lat: Double
) : Parcelable
@Parcelize
data class Main(
    val temp: Double,
    var tempMetric: Double,

    val feels_like: Double,

    val tempMin: Double,

    val tempMax: Double,

    val pressure: Long,
    val humidity: Long,

    val seaLevel: Long,

    val grndLevel: Long
) : Parcelable

@Parcelize
data class Sys(
    val type: Long,
    val id: Long,
    val country: String,
    val sunrise: Long,
    val sunset: Long
) : Parcelable
@Parcelize
data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
) : Parcelable
@Parcelize
data class Wind(
    val speed: Double,
    val deg: Long,
    val gust: Double
) : Parcelable
