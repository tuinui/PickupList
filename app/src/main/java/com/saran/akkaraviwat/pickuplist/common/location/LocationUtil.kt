package com.saran.akkaraviwat.pickuplist.common.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class LocationUtil(private val context: Context) {

    /**
     * Whether or not the GMS services on this device is available
     *
     * @return true if the GMS service is available, false otherwise
     */
    fun isGmsAvailable(): Boolean {
        val result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        return ConnectionResult.SUCCESS == result
    }



    /**
     * Returns true iff the current application has the required permissions
     * to call location services api
     */
    fun hasRequiredPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    fun getDistance(
        origin: Pair<Double, Double>,
        destination: Pair<Double, Double>
    ): Float {

        val destinationLocation = Location("").apply {
            latitude = destination.first
            longitude = destination.second
        }
        val originLocation = Location("").apply {
            latitude = origin.first
            longitude = origin.second
        }
        return originLocation.distanceTo(destinationLocation)
    }

}
