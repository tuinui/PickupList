package com.saran.akkaraviwat.pickuplist.common

import kotlinx.coroutines.Job

fun Job?.cancelIfActive() {
    this?.let {
        if (!isCancelled) {
            cancel()
        }
    }
}
