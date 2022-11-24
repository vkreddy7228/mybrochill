package com.example.brochill.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brochill.R
import com.example.brochill.ServiceBuilder
import com.example.brochill.`interface`.ApiInterface
import com.example.brochill.adapter.TweetsAdapter
import com.example.brochill.dataClass.TweetResponse
import com.example.brochill.util.AppCoreUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeScreen : AppCompatActivity(){

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mFloatingButton : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)

        mRecyclerView = findViewById(R.id.tweetsRecyclerView)
        mFloatingButton = findViewById(R.id.floatingButton)

        getTweets()
        mFloatingButton.setOnClickListener {
            val intent = Intent(this, NewTweet::class.java)
            startActivity(intent)
        }
    }

    private fun getTweets() {
        val api = ServiceBuilder.buildService(ApiInterface::class.java)
        AppCoreUtils.getAuthKey(this)?.let {
            api.getAllTweets(it).enqueue(object : Callback<List<TweetResponse>?> {
                override fun onResponse(
                    call: Call<List<TweetResponse>?>,
                    response: Response<List<TweetResponse>?>
                ) {
                    if (response.isSuccessful) {
                        for (tweets in response.body().toString()) {
                            mRecyclerView.apply {
                                layoutManager = LinearLayoutManager(this@HomeScreen)
                                adapter = TweetsAdapter(response.body()!!)
                            }
                        }
                    } else {
                        Toast.makeText(this@HomeScreen, response.message(), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<List<TweetResponse>?>, t: Throwable) {
                    Toast.makeText(this@HomeScreen, t.message,Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}