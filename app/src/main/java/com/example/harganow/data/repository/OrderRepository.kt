package com.example.harganow.data.repository

import com.example.harganow.data.source.Firestore.Companion.ColRef
import com.example.harganow.data.source.Firestore.Companion.DocRef
import com.example.harganow.domain.model.DataOrException
import com.example.harganow.domain.model.Order
import kotlinx.coroutines.tasks.await

class OrderRepository {
    val TAG = "OrderRepository"
    val collectionName = "order"

    suspend fun newOrder(order: Order) {
        try {
            ColRef(collectionName).add(order).await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getOrder(orderId: String): DataOrException<Order, Exception> {
        var orderDoe: DataOrException<Order, Exception> = DataOrException<Order, Exception>()
        try {
            orderDoe.data =
                DocRef(collectionName, orderId).get().await().toObject(Order::class.java)
        } catch (e: Exception) {
            orderDoe.exception = e
        }
        return orderDoe
    }

    suspend fun getUserOrders(userId: String): DataOrException<List<Order>, Exception> {
        var orderDoe: DataOrException<List<Order>, Exception> =
            DataOrException<List<Order>, Exception>()
        try {
            orderDoe.data = ColRef(collectionName).whereEqualTo("userId", userId).get().await()
                .toObjects(Order::class.java)
        } catch (e: Exception) {
            orderDoe.exception = e
        }
        return orderDoe
    }
}