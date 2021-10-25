package com.saran.akkaraviwat.pickuplist.common.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DeviceLocationRepository(
    private val context: Context,
    private val locationUtil: LocationUtil
) {

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }


    private val fallbackLocationClient: LocationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private val isGmsAvailable: Boolean
        get() = locationUtil.isGmsAvailable()


    private val locationRequestInterval = 10L
    private val locationRequestFastestInterval = 1L
    private val locationRequestMaxWaitTime = 10000L
    private val locationRequestNumUpdates = 1
    private val locationRequestPriority = LocationRequest.PRIORITY_HIGH_ACCURACY


    /**
     * Retrieves last location using Fused Location API
     */
    @SuppressLint("MissingPermission")
    suspend fun getLastLocation(): Location? {
        if (!locationUtil.hasRequiredPermissions()) {
            return null
        }

        return if (isGmsAvailable) {
            return suspendCoroutine { continuation ->
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location == null) continuation.resume(null)
                    else continuation.resume(location)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
            } ?: requestLocationUpdate()

        } else {
            getFallbackLastLocation()
        }

    }

    private suspend fun requestLocationUpdate(): Location? {
        return when {
            isGmsAvailable -> {
                requestOneTimeGmsLocationUpdate()
            }
            else -> {
                null
            }
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun requestOneTimeGmsLocationUpdate(): Location? {
        val request = LocationRequest.create()
        request.interval = locationRequestInterval
        request.fastestInterval = locationRequestFastestInterval
        request.maxWaitTime = locationRequestMaxWaitTime
        request.priority = locationRequestPriority
        request.numUpdates = locationRequestNumUpdates


        return suspendCoroutine { cont ->
            val locationCallback: LocationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    cont.resume(locationResult?.lastLocation)
                    fusedLocationClient.removeLocationUpdates(this)
                }
            }

            fusedLocationClient.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    /**
     * Retrieves last location using android.location.LocationManager
     */
    @SuppressLint("MissingPermission")
    private fun getFallbackLastLocation(): Location? {
        if (!locationUtil.hasRequiredPermissions()) {
            return null
        }
        val isGpsEnabled =
            fallbackLocationClient.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkAvailable =
            fallbackLocationClient.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return if (!isGpsEnabled && !isNetworkAvailable) {
            null
        } else {
            var gpsLocation: Location? = null
            var networkLocation: Location? = null
            if (isGpsEnabled) {
                gpsLocation =
                    fallbackLocationClient.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
            if (isNetworkAvailable) {
                networkLocation =
                    fallbackLocationClient.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }

            if (gpsLocation != null && networkLocation != null) {
                // compare both location and see which one is more accurate
                // the smaller the number, the more accurate the location
                if (gpsLocation.accuracy > networkLocation.accuracy) {
                    networkLocation
                } else {
                    gpsLocation
                }
            } else {
                gpsLocation ?: networkLocation
            }

        }
    }
}
