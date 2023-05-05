package com.example.harganow.data.repository

import ItemRepository
import android.util.Log
import com.example.harganow.data.auth.FireAuthRepository
import com.example.harganow.data.source.Firestore
import com.example.harganow.data.source.Firestore.Companion.ColRef
import com.example.harganow.data.source.Firestore.Companion.DocRef
import com.example.harganow.domain.model.Cart
import com.example.harganow.domain.model.CartItem
import com.example.harganow.domain.model.DataOrException
import com.example.harganow.domain.model.Item
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.domain.model.ItemPriceData
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

    suspend fun addToCart(premiseId: String, item: CartItem) {
        if (currentUser?.uid == null) {
            throw Exception("User not logged in")
        }
        val cart = getCart(premiseId)
        if (cart == null) {
            throw Exception("Cart not found")
        }
        val newItems = cart.data?.items?.toMutableList()
        newItems?.add(item)
        updateCart(premiseId, newItems!!)
    }

    suspend fun updateCart(premiseId: String, items: List<CartItem>) {
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
        val newItems = cart.data!!.items?.filter { item ->
            !items.any { it.containsKey(item.id) }
        }

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
        cart.data?.items?.map { item ->
            if (item.id == itemId) {
                item.count = itemCount
            }
        }
        updateCart(premiseId, cart.data!!.items!!)
    }

    suspend fun getCartData(premiseId: String): MutableList<Map<ItemPrice, Int>> {
        val itemRepository = ItemRepository()
//        val priceRepository = PriceRepository()
        var cartDoe: DataOrException<Cart, Exception>? = getCart(premiseId)
        if (cartDoe == null) {
            throw Exception("Cart not found")
        }

        val cart = cartDoe.data
        if (cart == null) {
            throw Exception("Cart not found")
        }

        val items = cart.items
        if (items == null) {
            throw Exception("Cart not found")
        }

        val itemIds = items.map { item -> item.id }
        var priceList = mutableListOf<ItemPriceData>()
        itemIds.map { item ->
            var temp = PriceRepository.getPriceWithPremiseAndItem(
                itemId = item.toString(),
                premiseId = premiseId
            )
            if (temp.data == null) {
                throw Exception("Price not found")
            }
            priceList.add(temp.data!!.first())
        }

        var itemPriceList = mutableListOf<Map<ItemPrice, Int>>()
        priceList.map { priceData ->
            var itemCount =
                items.find { item -> item.id!!.toString() == priceData.item_code }?.count
                    ?: 0
            itemPriceList.add(
                mapOf(
                    priceData.toItemPrice(
                        itemRepository,
                        premiseRepository = PremiseRepository()
                    ) to
                            itemCount
                )
            )
        }
        return itemPriceList
    }
}