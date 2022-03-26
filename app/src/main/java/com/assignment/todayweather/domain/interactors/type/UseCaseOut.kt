package com.assignment.todayweather.domain.interactors.type

import kotlinx.coroutines.flow.Flow

interface UseCaseOut<out OUT> {
      suspend fun execute(): Flow<OUT>
}