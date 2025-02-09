package com.example.mentalguru

import com.google.firebase.auth.FirebaseAuth

class AuthRepository(private val firebaseAuth: FirebaseAuth) {
    fun signup(email: String, password: String, onComplete: (Result<Boolean>) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(Result.success(true))
                } else {
                    onComplete(Result.failure(task.exception ?: Exception("Signup failed")))
                }
            }
    }

    fun login(email: String, password: String, onComplete: (Result<Boolean>) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(Result.success(true))
                } else {
                    onComplete(Result.failure(task.exception ?: Exception("Signup failed")))
                }
            }
    }
}
