package com.example.brochill.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.brochill.R
import com.example.brochill.ServiceBuilder
import com.example.brochill.`interface`.ApiInterface
import com.example.brochill.common.AppConstants
import com.example.brochill.dataClass.TweetRequest
import com.example.brochill.dataClass.TweetResponse
import com.example.brochill.util.AppCoreUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewTweet : AppCompatActivity() {

    private lateinit var mNewTweetNext: Button
    private lateinit var mTweetText: TextView
    private lateinit var mTweetPostProgressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tweet_screen)

        mNewTweetNext = findViewById(R.id.tweetPost)
        mTweetText = findViewById(R.id.tweetScreenText)
        mTweetPostProgressBar = findViewById(R.id.tweetPostProgressBar)

        mNewTweetNext.setOnClickListener {
            if (mTweetText.text.isEmpty()) {
                Toast.makeText(this, AppConstants.mEmptyTweet, Toast.LENGTH_SHORT).show()
            } else {
                mTweetPostProgressBar.visibility = View.VISIBLE
                mNewTweetNext.visibility = View.GONE
                tweetRequest()
            }
        }
    }

    private fun tweetRequest() {
        val api = ServiceBuilder.buildService(ApiInterface::class.java)
        AppCoreUtils.getAuthKey(applicationContext)?.let {
            api.tweetRequest(it, TweetRequest(mTweetText.text.toString()))
                .enqueue(object : Callback<TweetResponse?> {
                    override fun onResponse(
                        call: Call<TweetResponse?>,
                        response: Response<TweetResponse?>
                    ) {
                        if (response.isSuccessful) {
                            val intent = Intent(this@NewTweet, HomeScreen::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@NewTweet, response.message(), Toast.LENGTH_LONG)
                                .show()
                        }
                        mTweetPostProgressBar.visibility = View.GONE
                        mNewTweetNext.visibility = View.VISIBLE
                    }

                    override fun onFailure(call: Call<TweetResponse?>, t: Throwable) {
                        mTweetPostProgressBar.visibility = View.GONE
                        mNewTweetNext.visibility = View.VISIBLE
                        Toast.makeText(this@NewTweet, t.message, Toast.LENGTH_LONG).show()
                    }
                })
        }
    }
}