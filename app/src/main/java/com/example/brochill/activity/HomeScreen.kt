package com.example.brochill.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brochill.adapter.TweetsAdapter
import com.example.brochill.AppCoreUtils
import com.example.brochill.dataClass.TweetResponse
import com.example.brochill.`interface`.ApiInterface
import com.example.brochill.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeScreen : AppCompatActivity(){

    private var mBaseUrl: String = "https://wern-api.brochill.app/"
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)

        mRecyclerView = findViewById(R.id.tweetsRecyclerView)

        val retrofit = Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(ApiInterface::class.java)
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