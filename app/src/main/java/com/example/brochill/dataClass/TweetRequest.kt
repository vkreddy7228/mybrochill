package com.example.brochill.dataClass

import com.google.gson.annotations.SerializedName

data class TweetRequest(
    @SerializedName("tweet")
    var mTweet : String
    )
