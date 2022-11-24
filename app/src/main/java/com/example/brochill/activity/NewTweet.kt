package com.example.brochill.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.brochill.AppCoreUtils
import com.example.brochill.dataClass.TweetRequest
import com.example.brochill.dataClass.TweetResponse
import com.example.brochill.`interface`.ApiInterface
import com.example.brochill.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewTweet : AppCompatActivity() {

    private lateinit var mNewTweetNext : Button
    private lateinit var mTweetText : TextView

    private var mBaseUrl: String = "https://wern-api.brochill.app/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tweet_screen)

        mNewTweetNext = findViewById(R.id.tweetPost)
        mTweetText = findViewById(R.id.tweetScreenText)

        mNewTweetNext.setOnClickListener {
            tweetRequest()
        }
    }

    private fun tweetRequest() {
        val retrofit = Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(ApiInterface::class.java)

        AppCoreUtils.getAuthKey(applicationContext)?.let {
            api.tweetRequest(it,TweetRequest(mTweetText.text.toString())).enqueue(object : Callback<TweetResponse?> {
                override fun onResponse(
                    call: Call<TweetResponse?>,
                    response: Response<TweetResponse?>
                ) {
                    if (response.isSuccessful) {
                        Log.v("TweetResponse", response.body().toString())

                        val intent  = Intent(this@NewTweet, HomeScreen::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@NewTweet, response.message(), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<TweetResponse?>, t: Throwable) {
                    Toast.makeText(this@NewTweet, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}