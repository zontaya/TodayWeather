package com.assignment.todayweather.domain.interactors.type

import kotlinx.coroutines.flow.Flow

interface UseCaseIn<in IN> {
    suspend fun execute(param: IN): Flow<Unit>
}