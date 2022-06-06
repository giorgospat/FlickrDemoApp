package com.patronas.flickr.service

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

class LocationHelper {
    private val LOCATION_REFRESH_TIME: Long =
        20 * 1000 // 20 seconds. The Minimum Time to get location update
    private val LOCATION_REFRESH_DISTANCE =
        100f // 100 meters. The Minimum Distance to be changed to get location update

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(
        fusedLocationClient: FusedLocationProviderClient,
        locationCallback: LocationCallback
    ) {
        fusedLocationClient.requestLocationUpdates(
            getLocationRequest(),
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun getLocationRequest(): LocationRequest {
        val locationRequest: LocationRequest = LocationRequest.create()
        locationRequest.interval = LOCATION_REFRESH_TIME
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.smallestDisplacement = LOCATION_REFRESH_DISTANCE
        return locationRequest
    }

}