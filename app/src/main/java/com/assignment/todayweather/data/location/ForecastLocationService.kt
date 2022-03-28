package com.assignment.todayweather.data.location

import android.annotation.SuppressLint
import android.content.Context
import com.assignment.todayweather.data.remote.model.Coord
import com.assignment.todayweather.domain.ILocationManager
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class ForecastLocationService(private val context: Context) : ILocationManager {
    @SuppressLint("MissingPermission")
    override suspend fun getLastLocation(): Flow<Coord> = flow {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val location = suspendCoroutine<Coord> { continuation ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location == null)
                        continuation.resumeWithException(Exception("Location not available"))
                    else
                        continuation.resume(Coord(location.longitude, location.latitude))
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
        emit(location)
    }
}