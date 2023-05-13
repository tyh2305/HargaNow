package com.example.harganow.presentation.checkout

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.harganow.data.auth.FireAuthRepository
import com.example.harganow.data.repository.OrderRepository
import com.example.harganow.domain.model.Address
import com.example.harganow.domain.model.CartItem
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.domain.model.Order
import java.util.Date

@Composable
fun CheckOutScreen(
    navController: NavController,
    chosenList: MutableList<Map<ItemPrice, Int>>,
    address: MutableState<Address>
) {
    val authRepository: FireAuthRepository = FireAuthRepository()
    val orderRepository: OrderRepository = OrderRepository()
    var paymentOption = remember { mutableStateOf("") }
    val navigateToPreviousStack = {
        navController.popBackStack()
    }

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

        if (address.value.unitNumber == null) {
            throw Exception("Address incomplete")
        }

        if (paymentOption.value == "") {
            throw Exception("Please select a delivery option")
        }

        var newOrder: Order = Order(
            null,
            userId = authRepository.currentUser!!.id,
            timestamp = Date(),
            items = cartItemList,
            address = address.value,
            amount = totalPrice,
            paymentMethod = paymentOption.value
        )
        orderRepository.newOrder(newOrder)
        Toast.makeText(
            navController.context,
            "Order placed successfully",
            Toast.LENGTH_SHORT
        ).show()
        navController.navigate("home") {
            popUpTo("home") {
                inclusive = true
            }
        }
    }


    CheckOutBuilder(
        itemList = cloneList,
        address = address,
        paymentOption = paymentOption,
        handleCheckout = { handleCheckout() },
        navigateToAddress = { navController.navigate("address") },
        navigateToPreviousStack = { navigateToPreviousStack() },
    )
}