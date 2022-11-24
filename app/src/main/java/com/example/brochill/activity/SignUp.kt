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
import com.example.brochill.dataClass.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp : AppCompatActivity() {

    private lateinit var mSignUpNext: Button
    private lateinit var mFirstName: EditText
    private lateinit var mLastName: EditText
    private lateinit var mEmail: EditText
    private lateinit var mPassword: EditText
    private lateinit var mSignupProgressBar : ProgressBar

    var mTokenResponseSignup: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)

        mSignUpNext = findViewById(R.id.signUpNext)
        mFirstName = findViewById(R.id.signUpScreenFirstName)
        mLastName = findViewById(R.id.signUpScreenLastName)
        mEmail = findViewById(R.id.signUpScreenEmail)
        mPassword = findViewById(R.id.signUpScreenPassword)
        mSignupProgressBar = findViewById(R.id.signupProgressBar)

        mSignUpNext.setOnClickListener {
            if (mFirstName.text.isEmpty()) {
                Toast.makeText(this, AppConstants.mFirstNameValidation, Toast.LENGTH_SHORT).show()
            } else if (mLastName.text.isEmpty()) {
                Toast.makeText(this, AppConstants.mLastNameValidation, Toast.LENGTH_SHORT).show()
            } else if (mEmail.text.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail.text)
                    .matches()
            ) {
                Toast.makeText(this, AppConstants.mEmailValidation, Toast.LENGTH_SHORT).show()
            } else if (mPassword.text.isEmpty() || mPassword.text.length < AppConstants.mPasswordLength) {
                Toast.makeText(this, AppConstants.mPasswordValidation, Toast.LENGTH_SHORT).show()
            } else {
                mSignupProgressBar.visibility = View.VISIBLE
                mSignUpNext.visibility = View.GONE
                signupRequest()
            }
        }
    }

    private fun signupRequest() {
        val api = ServiceBuilder.buildService(ApiInterface::class.java)
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
                    mSignupProgressBar.visibility = View.GONE
                    mSignUpNext.visibility = View.VISIBLE
                }

                override fun onFailure(call: Call<LoginRegisterResponse>, t: Throwable) {
                    mSignupProgressBar.visibility = View.GONE
                    mSignUpNext.visibility = View.VISIBLE
                    Toast.makeText(this@SignUp, t.message, Toast.LENGTH_LONG).show()
                }
            })
    }
}