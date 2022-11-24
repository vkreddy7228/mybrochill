package com.example.brochill.dataClass

import com.google.gson.annotations.SerializedName

data class TweetResponse(

    @SerializedName("tweet")
    var mTweet : String,
    @SerializedName("_id")
    var mId : String,
    @SerializedName("__v")
    var mVersion : String
)
