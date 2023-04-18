package com.example.harganow.data.auth

import com.example.harganow.domain.model.AppUser
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FireAuthRepository {
    private val auth = Firebase.auth

    val isLoggedIn: Boolean
        get() = auth.currentUser != null
    val currentUser: AppUser?
        get() = auth.currentUser?.let { AppUser(it.uid, it.displayName, it.email) }

    // TODO: Add handle for different result, including wrong password etc.
    fun login(email: String, password: String, callback: (Result<Unit>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(Result.Success(Unit))
                } else {
                    val exception = task.exception as? FirebaseAuthException
                        ?: Exception("Unknown error occurred during login")
                    callback(Result.Failure(exception))
                }
            }
    }

    fun register(email: String, password: String, callback: (Result<Unit>) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(Result.Success(Unit))
                } else {
                    val exception = task.exception as? FirebaseAuthException
                        ?: Exception("Unknown error occurred during registration")
                    callback(Result.Failure(exception))
                }
            }
    }

    fun logout(callback: (Result<Unit>) -> Unit) {
        auth.signOut()
        callback(Result.Success(Unit))
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}