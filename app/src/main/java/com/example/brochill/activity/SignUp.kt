package com.example.brochill.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.brochill.dataClass.LoginRegisterResponse
import com.example.brochill.dataClass.RegisterRequest
import com.example.brochill.`interface`.ApiInterface
import com.example.brochill.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUp : AppCompatActivity() {

    private lateinit var mSignUpNext: Button
    private lateinit var mFirstName: EditText
    private lateinit var mLastName: EditText
    private lateinit var mEmail: EditText
    private lateinit var mPassword: EditText

    private var mBaseUrl: String = "https://wern-api.brochill.app/"

    var mTokenResponseSignup: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)

        mSignUpNext = findViewById(R.id.signUpNext)
        mFirstName = findViewById(R.id.signUpScreenFirstName)
        mLastName = findViewById(R.id.signUpScreenLasttName)
        mEmail = findViewById(R.id.signUpScreenEmail)
        mPassword = findViewById(R.id.signUpScreenPassword)

        mSignUpNext.setOnClickListener {
            signupRequest()
        }
    }

    private fun signupRequest() {
        val retrofit = Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        val api = retrofit.create(ApiInterface::class.java);

        api.registerRequest(
            RegisterRequest(
                mFirstName.text.toString(),
                mLastName.text.toString(),
                mEmail.text.toString(),
                mPassword.text.toString()
            )
        )
            .enqueue(object : Callback<LoginRegisterResponse> {
                override fun onResponse(
                    call: Call<LoginRegisterResponse>,
                    response: Response<LoginRegisterResponse>
                ) {
                    val responseBody = response.body()
                    if (response.isSuccessful) {
                        if (responseBody != null) {
                            mTokenResponseSignup = responseBody.mToken
                        }
                        val intent = Intent(this@SignUp, Login::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@SignUp,
                            response.message(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<LoginRegisterResponse>, t: Throwable) {
                    Toast.makeText(this@SignUp, t.message, Toast.LENGTH_LONG).show()
                }
            })
    }
}