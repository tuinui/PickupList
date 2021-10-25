package com.saran.akkaraviwat.pickuplist.common.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    protected val _showToastEvent = MutableLiveData<SingleLiveEvent<String>>()
    val showToastEvent = _showToastEvent.toLiveData()

    private val _showLoadingEvent = MutableLiveData<SingleLiveEvent<Boolean>>()
    val showLoadingEvent = _showLoadingEvent.toLiveData()

    protected fun CoroutineScope.showLoadingDebounceJob(delay: Long = 400): Job {
        return launch {
            delay(delay)
            try {
                showLoading()
                coroutineContext[Job]!!.join()
            } finally {
                dismissLoading()
            }
        }
    }

    open fun showLoading() {
        _showLoadingEvent.postValue(SingleLiveEvent(true))
    }

    open fun dismissLoading() {
        _showLoadingEvent.postValue(SingleLiveEvent(false))
    }

    open fun showToast(message: String?) {
        viewModelScope.launch {
            message?.let { _showToastEvent.value = SingleLiveEvent(it) }
        }
    }
}
