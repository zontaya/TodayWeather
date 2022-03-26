package com.assignment.todayweather.domain.interactors.type

import kotlinx.coroutines.flow.Flow

interface UseCaseInOut<in IN, out OUT> {
    suspend fun execute(param: IN): Flow<OUT>
}