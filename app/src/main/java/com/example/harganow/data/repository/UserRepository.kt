package com.example.harganow.data.repository

import android.util.Log
import com.example.harganow.data.auth.FireAuthRepository
import com.example.harganow.data.source.Firestore
import com.example.harganow.data.source.Firestore.Companion.db
import com.example.harganow.domain.model.AppUser
import com.google.firebase.firestore.DocumentReference
import javax.inject.Singleton

@Singleton
class UserRepository {
    val TAG = "UserRepository"
    val authRepository: FireAuthRepository = FireAuthRepository()
    val currentUser = authRepository.getCurrentUser()
    var user: AppUser = AppUser()
    fun UserRef(): DocumentReference? {
        if (currentUser?.uid != null)
            return Firestore.getDocument("user", currentUser.uid)
        else
            return null
    }

    fun createNewUser(user: AppUser) {
        user.id?.let {
            db.collection("user").document(it).set(user).addOnSuccessListener {
                Log.d(TAG, "User created")
            }.addOnFailureListener { exception ->
                Log.d(TAG, "User not created: $exception")
            }
        }
    }

    fun fetchUser(): AppUser? {
        var userId = currentUser?.uid
        userId?.let {
            db.collection("user").document(it).get().addOnSuccessListener { document ->
                if (document != null) {
                    user = document.toObject(AppUser::class.java)!!
                } else {
                    return@addOnSuccessListener
                }
            }.addOnFailureListener { exception ->
                Log.d(TAG, "Data not found: $exception")
                return@addOnFailureListener
            }
        }


        return user
    }

    fun updateUser(user: AppUser) {
        user.id?.let {
            db.collection("user").document(it).set(user).addOnSuccessListener {
                Log.d(TAG, "User updated")
            }.addOnFailureListener { exception ->
                Log.d(TAG, "User not updated: $exception")
            }
        }
    }

    fun changeUserName(name: String) {
        // TODO: Implement change name on auth and database
        if (UserRef() == null) {
            return
        }
        UserRef()!!.update("name", name).addOnSuccessListener {
            Log.d(TAG, "User name updated")
        }.addOnFailureListener { exception ->
            Log.d(TAG, "User name not updated: $exception")
        }
    }

    fun getUserCartItem(): List<Int>? {
        // Get current user id
//        if (UserRef() == null) {
//            return null
//        }
        var itemListId: List<Int> = listOf<Int>()
//        TODO: Implement This
//        UserRef()!!.get().addOnSuccessListener { document ->
//            if (document != null) {
//                itemListId = document.get("cart") as List<Int>
//            } else {
//                return@addOnSuccessListener
//            }
//        }.addOnFailureListener { exception ->
//            Log.d(TAG, "Data not found: $exception")
//            return@addOnFailureListener
//        }
        itemListId = listOf(129, 235, 1566)

        return itemListId
    }


}