package com.saran.akkaraviwat.pickuplist.common.domain

import com.saran.akkaraviwat.pickuplist.common.CoroutineDispatcherProvider
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


abstract class UseCase<in P, R>() : KoinComponent {

    protected val dispatcherProvider: CoroutineDispatcherProvider by inject()

    @Throws(Exception::class)
    protected abstract suspend fun execute(params: P): R


    open suspend fun invoke(
        params: P
    ): ResultWrapper<R> {
        return try {
            val response = withContext(dispatcherProvider.io()) {
                execute(params)
            }
            ResultWrapper.Success(response)
        } catch (e: Exception) {

            ResultWrapper.Failure(e)
        }
    }

}


