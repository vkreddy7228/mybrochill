package com.example.brochill.dataClass

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val mEmail: String,
    @SerializedName("password")
    val mPassword: String
)
