package com.example.brochill

import com.example.brochill.common.AppConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()

    private val retrofitBuilder = Retrofit.Builder().baseUrl(AppConstants.mBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service : Class<T>): T {
        return retrofitBuilder.create(service)
    }
}