package com.example.brochill.dataClass

import com.google.gson.annotations.SerializedName

data class LoginRegisterResponse(
    @SerializedName("first_name")
    var mFirstName: String? = null,
    @SerializedName("last_name")
    var mLastName: String? = null,
    @SerializedName("_id")
    var mId: String? = null,
    @SerializedName("email")
    var mEmail: String? = null,
    @SerializedName("token")
    var mToken: String? = null
)