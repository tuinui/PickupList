package com.saran.akkaraviwat.pickuplist.pickuplist.domain

import com.saran.akkaraviwat.pickuplist.common.domain.UseCase
import com.saran.akkaraviwat.pickuplist.common.location.DeviceLocationRepository
import com.saran.akkaraviwat.pickuplist.common.location.LocationUtil
import com.saran.akkaraviwat.pickuplist.pickuplist.data.PickupListResponse
import com.saran.akkaraviwat.pickuplist.pickuplist.data.PickupRepository

class GetPickupListUseCase(
    private val pickupRepository: PickupRepository,
    private val locationUtil: LocationUtil,
    private val deviceLocationRepository: DeviceLocationRepository
) : UseCase<Unit, List<PickupDomainModel>>() {

    override suspend fun execute(params: Unit): List<PickupDomainModel> {
        val pickupListResponse = pickupRepository.getPickupLocations().pickupList
        val currentDeviceLocation = deviceLocationRepository.getLastLocation()

        val sortedPickupLocationResponse: List<PickupListResponse.Pickup> =
            if (currentDeviceLocation != null) {
                pickupListResponse.sortedBy {
                    if (it.latitude != null && it.longitude != null) {
                        locationUtil.getDistance(
                            origin = currentDeviceLocation.latitude to currentDeviceLocation.longitude,
                            destination = it.latitude to it.longitude
                        )
                    } else {
                        Float.MAX_VALUE
                    }
                }
            } else {
                pickupListResponse
            }

        return sortedPickupLocationResponse
            .map {
                PickupDomainModel(
                    alias = it.alias,
                    address = it.address1,
                    city = it.city
                )
            }
    }


}
