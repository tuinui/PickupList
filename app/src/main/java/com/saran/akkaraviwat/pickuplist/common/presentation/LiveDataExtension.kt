package com.saran.akkaraviwat.pickuplist.common.presentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

@Suppress("detekt.UnsafeCast")
fun <T> MutableLiveData<T>.toLiveData() = this as LiveData<T>

fun <T> LifecycleOwner.singleEventObserve(
    liveData: LiveData<SingleLiveEvent<T>>,
    body: (T) -> Unit = {}
) {
    liveData.observe(this, SingleEventObserver { it?.let { t -> body(t) } })
}

fun <T> Fragment.singleEventObserve(
    liveData: LiveData<SingleLiveEvent<T>>,
    body: (T) -> Unit = {}
) {
    liveData.observe(viewLifecycleOwner, SingleEventObserver { it?.let { t -> body(t) } })
}
