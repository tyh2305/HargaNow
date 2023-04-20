package com.example.harganow.data.repository

import com.example.harganow.data.auth.FireAuthRepository
import com.example.harganow.data.source.Firestore
import com.google.firebase.firestore.DocumentReference

class UserRepository {
    val TAG = "UserRepository"
    val authRepository: FireAuthRepository = FireAuthRepository()
    val currentUser = authRepository.getCurrentUser()

    fun UserRef(): DocumentReference? {
        if (currentUser?.uid != null)
            return Firestore.getDocument("user", currentUser.uid)
        else
            return null
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