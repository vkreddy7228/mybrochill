package com.example.brochill.dataClass

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("first_name")
    val mFirstName : String,
    @SerializedName("last_name")
    val mLastName : String,
    @SerializedName("email")
    val mEmail : String,
    @SerializedName("password")
    val mPassword : String
    )
