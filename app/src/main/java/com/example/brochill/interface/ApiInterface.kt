package com.example.brochill.`interface`

import com.example.brochill.dataClass.*
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @POST("register")
    fun registerRequest(@Body registerRequest: RegisterRequest): Call<LoginRegisterResponse>

    @POST("login")
    fun loginRequest(@Body loginRequest: LoginRequest): Call<LoginRegisterResponse>

    @POST("tweets")
    fun tweetRequest(
        @Header("x-api-key") apiKey: String,
        @Body tweet: TweetRequest
    ): Call<TweetResponse>

    @GET("tweets")
    fun getAllTweets(@Header("x-api-key") apiKey: String): Call<List<TweetResponse>>
}