package com.assignment.todayweather.domain.interactors

import com.assignment.todayweather.data.remote.model.Coord
import com.assignment.todayweather.domain.ILocationManager
import com.assignment.todayweather.domain.interactors.type.UseCaseOut
import kotlinx.coroutines.flow.Flow

class GetLocationUseCase(
    private val location: ILocationManager
) : UseCaseOut<Coord> {
    override suspend fun execute(): Flow<Coord> =
        location.getLastLocation()
}



