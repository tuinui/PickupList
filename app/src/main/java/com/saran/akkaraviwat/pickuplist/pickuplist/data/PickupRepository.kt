package com.saran.akkaraviwat.pickuplist.pickuplist.data

class PickupRepository(private val pickupRemoteDataSource: PickupRemoteDataSource) {


    suspend fun getPickupLocations(): PickupListResponse {
        return pickupRemoteDataSource.getPickupLocations()
    }
}
