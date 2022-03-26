package com.assignment.todayweather.domain.interactors.type

import kotlinx.coroutines.flow.Flow

interface UseCase {
    suspend fun execute(): Flow<Unit>
}