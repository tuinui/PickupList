package com.saran.akkaraviwat.pickuplist.common

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Saran Akkaraviwat on 2019-09-27.
 * saran.a@linecorp.com
 */
interface CoroutineDispatcherProvider {
    fun io(): CoroutineDispatcher

    fun main(): CoroutineDispatcher
}
