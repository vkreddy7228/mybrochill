package com.example.brochill.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.brochill.AppCoreUtils
import com.example.brochill.dataClass.LoginRequest
import com.example.brochill.dataClass.LoginRegisterResponse
import com.example.brochill.`interface`.ApiInterface
import com.example.brochill.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity(){

    private lateinit var mLoginNext : Button
    private lateinit var mLoginEmail : EditText
    private lateinit var mLoginPassword : EditText

    private var mBaseUrl : String = "https://wern-api.brochill.app/"


    var mTokenResponseLogin : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        mLoginNext = findViewById(R.id.loginNext)
        mLoginEmail = findViewById(R.id.loginScreenEmail)
        mLoginPassword = findViewById(R.id.loginScreenPassword)

        mLoginNext.setOnClickListener {
            loginRequest()
        }
    }

    private fun loginRequest() {
        val retrofit = Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        val api = retrofit.create(ApiInterface::class.java);

        api.loginRequest(LoginRequest(mLoginEmail.text.toString(), mLoginPassword.text.toString()))
            .enqueue(object : Callback<LoginRegisterResponse?> {
                override fun onResponse(
                    call: Call<LoginRegisterResponse?>,
                    response: Response<LoginRegisterResponse?>
                ) {
                    val responseBody = response.body()
                    if (response.isSuccessful) {
                        if (responseBody != null) {
                            mTokenResponseLogin = responseBody.mToken
                            mTokenResponseLogin?.let { AppCoreUtils.setAuthKey(this@Login, it) }
                        }
                        val intent  = Intent(this@Login, NewTweet::class.java)
                        startActivity(intent)
                    } else  {
                        Toast.makeText(this@Login, response.message(), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<LoginRegisterResponse?>, t: Throwable) {
                    Toast.makeText(this@Login, t.message, Toast.LENGTH_LONG).show()
                }
            })
    }

}