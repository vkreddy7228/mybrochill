package com.example.brochill.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.brochill.R

class Welcome : AppCompatActivity() {

    private lateinit var mNextWelcomeScreen: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_screen)

        mNextWelcomeScreen = findViewById(R.id.welcomeNextButton)

        mNextWelcomeScreen.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}