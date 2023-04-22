package com.example.harganow.data.repository

import android.util.Log
import com.example.harganow.data.auth.FireAuthRepository
import com.example.harganow.data.source.Firestore
import com.example.harganow.data.source.Firestore.Companion.ColRef
import com.example.harganow.data.source.Firestore.Companion.DocRef
import com.example.harganow.domain.model.Cart
import com.example.harganow.domain.model.DataOrException
import kotlinx.coroutines.tasks.await

class CartRepository {
    val TAG = "CartRepository"
    val collectionName = "cart"
    val authRepository: FireAuthRepository = FireAuthRepository()
    val currentUser = authRepository.getCurrentUser()

    suspend fun getCart(premiseId: String): DataOrException<Cart, Exception>? {
        val doe: DataOrException<Cart, Exception> = DataOrException<Cart, Exception>()
        if (currentUser?.uid == null) {
            doe.exception = Exception("User not logged in")
            return doe
        }
        try {
            doe.data = DocRef(collectionName, currentUser.uid + premiseId).get().await()
                .toObject(Cart::class.java)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting documents: ", e)
            doe.exception = e
        }
        return doe
    }

    suspend fun updateCart(premiseId: String, items: List<Map<Int, Int>>) {
        if (currentUser?.uid == null) {
            throw Exception("User not logged in")
        }
        val cart = Cart(currentUser.uid + premiseId, items)
        try {
            DocRef(collectionName, cart.id!!).set(cart).await()
        } catch (e: Exception) {
            Log.e(TAG, "Error adding to cart: ", e)
            throw e
        }
    }

    suspend fun removeFromCart(premiseId: String, items: List<Map<Int, Int>>) {
        if (currentUser?.uid == null) {
            throw Exception("User not logged in")
        }
        val cart = getCart(premiseId)
        if (cart == null) {
            throw Exception("Cart not found")
        }
        val newItems = cart.data?.items?.filter { item -> !items.contains(item) }
        updateCart(premiseId, newItems!!)
    }

    suspend fun updateCount(premiseId: String, itemId: Int, itemCount: Int) {
        if (currentUser?.uid == null) {
            throw Exception("User not logged in")
        }
        val cart = getCart(premiseId)
        if (cart == null) {
            throw Exception("Cart not found")
        }
        val newItems = cart.data?.items?.map { item ->
            if (item.containsKey(itemId)) {
                mapOf(itemId to itemCount)
            } else {
                item
            }
        }
        updateCart(premiseId, newItems!!)
    }
}
