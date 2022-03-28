package com.assignment.todayweather.domain

import com.assignment.todayweather.data.remote.model.Coord
import kotlinx.coroutines.flow.Flow

interface ILocationManager {
    suspend fun getLastLocation(): Flow<Coord>
}