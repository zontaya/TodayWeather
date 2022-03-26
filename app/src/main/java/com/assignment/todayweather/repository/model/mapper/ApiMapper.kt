package com.assignment.todayweather.repository.model.mapper

import com.assignment.todayweather.domain.Mapper
import com.assignment.todayweather.data.remote.model.Forecast

class ApiUiMapper : Mapper<Forecast, Forecast> {
    override fun map(data: Forecast): Forecast = data.run {
        this.main.tempMetric = ((this.main.temp - 32) * 5) / 9
        this
    }
}