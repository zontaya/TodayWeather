package com.assignment.todayweather.repository.model.mapper

import com.assignment.todayweather.data.remote.model.Forecast
import com.assignment.todayweather.data.remote.model.ForecastDetail
import com.assignment.todayweather.domain.Mapper
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ApiUiMapper : Mapper<Forecast, Forecast> {
    override fun map(data: Forecast): Forecast = data.run {
        val iconName = data.weather[0].icon
        val url = "http://openweathermap.org/img/wn/$iconName@4x.png"
        weather[0].icon = url
        this
    }
}

class ApiDetailUiMapper : Mapper<ForecastDetail, ForecastDetail> {
    override fun map(data: ForecastDetail): ForecastDetail = data.run {
        this.hourly.map { m ->
            val iconName = m.weather[0].icon
            val url = "http://openweathermap.org/img/wn/$iconName@2x.png"
            m.weather[0].icon = url
            m
        }
        this
    }

}