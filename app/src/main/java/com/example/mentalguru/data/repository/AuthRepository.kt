package com.example.mentalguru.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth

class AuthRepository(private val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    private val firebaseAuth = FirebaseAuth.getInstance()

    //Check if user is logged in by checking Firebase or SharedPreferences
    fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null || sharedPreferences.getString("email", null) != null
    }

    //Store email when user logs in
    fun saveEmail(email: String) {
        sharedPreferences.edit().putString("email", email).apply()
    }

    fun saveLoginState(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
    }

    //Get stored email
    fun getStoredEmail(): String? {
        return sharedPreferences.getString("email", null)
    }

    //Clear stored data on logout
    fun clearUserData() {
        sharedPreferences.edit().clear().apply()
    }
}
