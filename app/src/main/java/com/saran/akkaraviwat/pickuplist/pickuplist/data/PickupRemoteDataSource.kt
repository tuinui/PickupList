package com.saran.akkaraviwat.pickuplist.pickuplist.data

import retrofit2.http.GET

interface PickupRemoteDataSource {

    @GET("pickup-locations")
    suspend fun getPickupLocations(): PickupListResponse
}
