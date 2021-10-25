package com.saran.akkaraviwat.pickuplist.pickuplist.data

import com.saran.akkaraviwat.pickuplist.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


object PickupHttpInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequestBuilder = originalRequest.newBuilder()

        // replace/add value to the headers
        setupHeaders(newRequestBuilder)

        return chain.proceed(newRequestBuilder.build())
    }

    private fun setupHeaders(newRequestBuilder: Request.Builder) {
        newRequestBuilder.addHeader("x-api-key", BuildConfig.API_KEY.toString())
    }

}
