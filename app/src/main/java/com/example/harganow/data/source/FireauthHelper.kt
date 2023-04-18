package com.example.harganow.data.source

import com.google.firebase.auth.FirebaseAuth

class FireauthHelper {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getCurrentUser() = auth.currentUser

    fun createUserWithEmailAndPassword(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)

    fun signInWithEmailAndPassword(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)

    fun signOut() = auth.signOut()

    companion object {
        @Volatile
        private var INSTANCE: FireauthHelper? = null

        fun getInstance(): FireauthHelper {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = FireauthHelper()
                INSTANCE = instance
                return instance
            }
        }
    }
}