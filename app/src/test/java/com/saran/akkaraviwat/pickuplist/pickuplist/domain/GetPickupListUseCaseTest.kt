package com.saran.akkaraviwat.pickuplist.pickuplist.domain

import com.saran.akkaraviwat.pickuplist.common.location.DeviceLocationRepository
import com.saran.akkaraviwat.pickuplist.common.location.LocationUtil
import com.saran.akkaraviwat.pickuplist.pickuplist.data.PickupListResponse
import com.saran.akkaraviwat.pickuplist.pickuplist.data.PickupRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContain
import org.junit.Before
import org.junit.Test

class GetPickupListUseCaseTest {


    @MockK
    private lateinit var pickupRepository: PickupRepository

    @MockK
    private lateinit var locationUtil: LocationUtil

    @MockK
    private lateinit var deviceLocationRepository: DeviceLocationRepository

    private lateinit var getPickupListUseCase: GetPickupListUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getPickupListUseCase = GetPickupListUseCase(
            pickupRepository = pickupRepository,
            locationUtil = locationUtil,
            deviceLocationRepository = deviceLocationRepository
        )
    }


    @Test
    fun `Should filter only active == true`() {
        runBlocking {
            // arrange
            val fakeData = PickupListResponse.Pickup.FAKE_DATA
            coEvery { pickupRepository.getPickupLocations() }.returns(
                PickupListResponse(
                    listOf(
                        fakeData.copy(alias = "aliasA", active = true),
                        fakeData.copy(alias = "aliasB", active = false)
                    )
                )
            )
            coEvery { deviceLocationRepository.getLastLocation() }.returns(null)

            // act
            val result = getPickupListUseCase.forceExecute(Unit)

            // assert
            result.size shouldBeEqualTo 1
            result.shouldContain(
                PickupDomainModel(
                    alias = "aliasA",
                    address = fakeData.address1,
                    city = fakeData.city
                )
            )
        }

    }
}
