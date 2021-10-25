package com.saran.akkaraviwat.pickuplist.pickuplist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saran.akkaraviwat.pickuplist.R
import com.saran.akkaraviwat.pickuplist.common.domain.isSuccessNonNull
import com.saran.akkaraviwat.pickuplist.common.domain.success
import com.saran.akkaraviwat.pickuplist.common.presentation.BaseViewModel
import com.saran.akkaraviwat.pickuplist.common.presentation.toLiveData
import com.saran.akkaraviwat.pickuplist.pickuplist.domain.GetPickupListUseCase
import kotlinx.coroutines.launch

class PickupListViewModel(
    private val application: Application,
    private val getPickupListUseCase: GetPickupListUseCase
) : BaseViewModel() {

    private val _pickupList = MutableLiveData<List<PickupItemUiModel>>()
    val pickupList: LiveData<List<PickupItemUiModel>> = _pickupList.toLiveData()

    fun loadPickupList() = viewModelScope.launch {
        showLoading()
        val result = getPickupListUseCase.invoke(Unit)
        dismissLoading()
        if (result.isSuccessNonNull) {
            val pickupListUiModel = result.success()
                .filter { it.alias?.isNotBlank() == true }
                .map { PickupItemUiModel(alias = it.alias, address = it.address, city = it.city) }
            _pickupList.postValue(pickupListUiModel)
        } else {
            // oops something went wrong
            showToast(application.getString(R.string.sorry_something_went_wrong))
        }
    }

}
