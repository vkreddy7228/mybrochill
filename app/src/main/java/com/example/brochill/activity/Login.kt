package com.example.brochill.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.brochill.R
import com.example.brochill.ServiceBuilder
import com.example.brochill.`interface`.ApiInterface
import com.example.brochill.common.AppConstants
import com.example.brochill.dataClass.LoginRegisterResponse
import com.example.brochill.dataClass.LoginRequest
import com.example.brochill.util.AppCoreUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    private lateinit var mLoginNext: Button
    private lateinit var mLoginEmail: EditText
    private lateinit var mLoginPassword: EditText
    private lateinit var mLoginProgressBar : ProgressBar


    var mTokenResponseLogin: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        mLoginNext = findViewById(R.id.loginNext)
        mLoginEmail = findViewById(R.id.loginScreenEmail)
        mLoginPassword = findViewById(R.id.loginScreenPassword)
        mLoginProgressBar = findViewById(R.id.loginProgressBar)

        mLoginNext.setOnClickListener {
            if (mLoginEmail.text.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(
                    mLoginEmail.text
                ).matches()
            ) {
                Toast.makeText(this, AppConstants.mEmailValidation, Toast.LENGTH_SHORT).show()
            } else if (mLoginPassword.text.isEmpty()) {
                Toast.makeText(this, AppConstants.mPasswordRequired, Toast.LENGTH_SHORT).show()
            } else {
                mLoginNext.visibility = View.GONE
                mLoginProgressBar.visibility = View.VISIBLE
                loginRequest()
            }
        }
    }

    private fun loginRequest() {
        val api = ServiceBuilder.buildService(ApiInterface::class.java)
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
                        val intent = Intent(this@Login, NewTweet::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@Login, response.message(), Toast.LENGTH_LONG).show()
                    }
                    mLoginNext.visibility = View.VISIBLE
                    mLoginProgressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<LoginRegisterResponse?>, t: Throwable) {
                    mLoginNext.visibility = View.VISIBLE
                    mLoginProgressBar.visibility = View.GONE
                    Toast.makeText(this@Login, t.message, Toast.LENGTH_LONG).show()
                }
            })
    }

}