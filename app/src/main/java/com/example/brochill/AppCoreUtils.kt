package com.example.brochill

import android.content.Context

class AppCoreUtils {
    companion object {
        fun setAuthKey(context: Context, value: String) {
            val sharedPref =
                context.getSharedPreferences("com.example.brochill", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("token", value).apply()
            }
        }

        fun getAuthKey(context: Context): String? {
            val sharedPref =
                context.getSharedPreferences("com.example.brochill", Context.MODE_PRIVATE)
            return sharedPref.getString("token", null)
        }
    }
}