package com.saran.akkaraviwat.pickuplist.pickuplist

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.saran.akkaraviwat.pickuplist.CoroutinesTestRule
import com.saran.akkaraviwat.pickuplist.R
import com.saran.akkaraviwat.pickuplist.common.domain.ResultWrapper
import com.saran.akkaraviwat.pickuplist.common.presentation.getTestValue
import com.saran.akkaraviwat.pickuplist.pickuplist.domain.GetPickupListUseCase
import com.saran.akkaraviwat.pickuplist.pickuplist.domain.PickupDomainModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PickupListViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var application: Application

    @MockK
    private lateinit var getPickupListUseCase: GetPickupListUseCase

    private lateinit var viewModel: PickupListViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = PickupListViewModel(
            application = application,
            getPickupListUseCase = getPickupListUseCase
        )
    }

    @Test
    fun `Should show pickup list When getPickupListUseCase is success`() {
        // arrange
        coEvery { getPickupListUseCase.invoke(Unit) }.returns(
            ResultWrapper.Success(
                listOf(
                    PickupDomainModel(
                        alias = "alias",
                        address = "address",
                        city = "city"
                    ),
                    PickupDomainModel(
                        alias = null,
                        address = "address",
                        city = "city"
                    )
                )
            )
        )

        // act
        viewModel.loadPickupList()

        // assert
        val pickupList = viewModel.pickupList.getTestValue()
        pickupList!!.size shouldBeEqualTo 1
        pickupList shouldBeEqualTo listOf(
            PickupItemUiModel(
                alias = "alias",
                address = "address",
                city = "city"
            )
        )

    }

    @Test
    fun `Should show error When getPickupListUseCase is failed`() {
        // arrange
        coEvery { getPickupListUseCase.invoke(Unit) }.returns(
            ResultWrapper.Failure(NullPointerException())
        )
        val expectedErrorMessage = "Sorry something went wrong"
        every { application.getString(R.string.sorry_something_went_wrong) }.returns(
            expectedErrorMessage
        )

        // act
        viewModel.loadPickupList()

        // assert
        val toastValue = viewModel.showToastEvent.getTestValue()!!.peek()
        toastValue.shouldBeEqualTo(expectedErrorMessage)

    }

}
