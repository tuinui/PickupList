package com.saran.akkaraviwat.pickuplist.common.presentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@Suppress("detekt.UnsafeCast")
fun <T> MutableLiveData<T>.toLiveData() = this as LiveData<T>


@Throws(InterruptedException::class)
fun <T> LiveData<T>.getTestValue(timeout: Long = 300, timeUnit: TimeUnit = TimeUnit.MILLISECONDS): T? {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = (object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            removeObserver(this)
        }
    }).also {
        observeForever(it)
    }
    latch.await(timeout, timeUnit)

    return data
}
