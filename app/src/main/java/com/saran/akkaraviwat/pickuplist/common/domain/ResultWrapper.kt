package com.saran.akkaraviwat.pickuplist.common.domain

sealed class ResultWrapper<out T> {

    data class Success<out T>(val data: T) : ResultWrapper<T>()

    data class Failure(val exception: Exception?) : ResultWrapper<Nothing>()

}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val ResultWrapper<*>.isSuccess
    get() = this is ResultWrapper.Success


val ResultWrapper<*>.isFailure
    get() = this is ResultWrapper.Failure

val ResultWrapper<*>.isSuccessNonNull get() = isSuccess && successOr(null) != null

fun <T> ResultWrapper<T>.successOr(fallback: T): T {
    return (this as? ResultWrapper.Success<T>)?.data ?: fallback
}

fun <T> ResultWrapper<T>.success(): T {
    return (this as ResultWrapper.Success<T>).data
}

