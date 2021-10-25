package com.saran.akkaraviwat.pickuplist.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Created by Saran Akkaraviwat on 2019-09-27.
 * saran.a@linecorp.com
 */
object FakeCoroutineDispatcherProvider : CoroutineDispatcherProvider {

    override fun io(): CoroutineDispatcher {
        return Dispatchers.Unconfined
    }

    override fun main(): CoroutineDispatcher {
        return Dispatchers.Unconfined
    }

}
