package com.example.brochill.util

import android.content.Context
import com.example.brochill.common.AppConstants

class AppCoreUtils {
    companion object {
        fun setAuthKey(context: Context, value: String) {
            val sharedPref =
                context.getSharedPreferences("com.example.brochill", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString(AppConstants.mToken, value).apply()
            }
        }

        fun getAuthKey(context: Context): String? {
            val sharedPref =
                context.getSharedPreferences("com.example.brochill", Context.MODE_PRIVATE)
            return sharedPref.getString(AppConstants.mToken, null)
        }
    }
}