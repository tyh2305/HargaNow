package com.example.harganow.data.auth

import com.example.harganow.domain.model.AppUser
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FireAuthRepository {
    private val auth = Firebase.auth

    val isLoggedIn: Boolean
        get() = auth.currentUser != null
    val currentUser: AppUser?
        get() = auth.currentUser?.let { AppUser(it.uid, it.displayName, it.email) }

    // TODO: Add handle for different result, including wrong password etc.
    suspend fun login(email: String, password: String, callback: (Result<Unit>) -> Unit) {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            callback(Result.Success(Unit))
        } catch (e: Exception) {
            if (e is FirebaseAuthException) {
                callback(Result.Failure(e))
            } else {
                callback((Result.Failure(Exception("Unknown error occurred during login"))))
            }
        }


//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    callback(Result.Success(Unit))
//                } else {
//                    val exception = task.exception as? FirebaseAuthException
//                        ?: Exception("Unknown error occurred during login")
//                    callback(Result.Failure(exception))
//                }
//            }
    }

    suspend fun register(email: String, password: String, callback: (Result<Any>) -> Unit) {
        return try {
            val user = auth.createUserWithEmailAndPassword(email, password).await()
            if (user.user == null) {
                callback(Result.Failure(Exception("Unknown error occurred during registration")))
            }
            callback(Result.Success(user.user!!.uid))
        } catch (e: Exception) {
            if (e is FirebaseAuthException) {
                callback(Result.Failure(e))
            } else {
                callback((Result.Failure(Exception("Unknown error occurred during registration"))))
            }
        }
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    callback(Result.Success(Unit))
//                } else {
//                    val exception = task.exception as? FirebaseAuthException
//                        ?: Exception("Unknown error occurred during registration")
//                    callback(Result.Failure(exception))
//                }
//            }
    }

    fun logout(callback: (Result<Unit>) -> Unit) {
        auth.signOut()
        callback(Result.Success(Unit))
    }

    suspend fun resetPassword(callback: (Result<Unit>) -> Unit) {
        var email = currentUser?.email ?: ""
        if (email.isEmpty()) {
            callback(Result.Failure(Exception("Current user is not logged in")))
        }
        return try {
            auth.sendPasswordResetEmail(email).await()
            callback(Result.Success(Unit))
        } catch (e: Exception) {
            if (e is FirebaseAuthException) {
                callback(Result.Failure(e))
            } else {
                callback((Result.Failure(Exception("Unknown error occurred during reset password"))))
            }
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}