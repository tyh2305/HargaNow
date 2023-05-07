package com.example.harganow.presentation.checkout

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.harganow.data.auth.FireAuthRepository
import com.example.harganow.data.repository.OrderRepository
import com.example.harganow.domain.model.Address
import com.example.harganow.domain.model.CartItem
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.domain.model.Order
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date
import java.time.LocalDate

@Composable
fun CheckOutScreen(
    navigateToPreviousStack: () -> Unit,
    chosenList: MutableList<Map<ItemPrice, Int>>
) {
    val authRepository: FireAuthRepository = FireAuthRepository()
    val orderRepository: OrderRepository = OrderRepository()
    val TAG = "CheckOutScreen"
    Log.d(TAG, "CheckOutScreen: chosenList: $chosenList")
    var cloneList = mutableListOf<Map<ItemPrice, Int>>()
    cloneList.addAll(chosenList)

    suspend fun handleCheckout() {
        var cartItemList = mutableListOf<CartItem>()
        var totalPrice: Double = 0.0
        chosenList.forEach { item ->
            cartItemList.add(CartItem(item.keys.first().item.id!!.toInt(), item.values.first()))
            totalPrice += item.keys.first().price * item.values.first()
        }

        val mockAddress: Address = Address(
            null,
            "1234",
            "TARUMT",
            "Jalan Genting Kelang",
            "53300",
            "Setapak",
            "W.P. Kuala Lumpur"
        )

        var newOrder: Order = Order(
            null,
            userId = authRepository.currentUser!!.id,
            timestamp = Date(),
            items = cartItemList,
            address = mockAddress,
            amount = totalPrice,
            paymentMethod = "Cash on Delivery"
        )
        orderRepository.newOrder(newOrder)
        navigateToPreviousStack()
    }


    CheckOutBuilder(
        itemList = cloneList,
        navigateToOrder = { handleCheckout() },
        navigateToPreviousStack = navigateToPreviousStack
    )
}