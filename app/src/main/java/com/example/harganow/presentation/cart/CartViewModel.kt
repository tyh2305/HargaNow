package com.example.harganow.presentation.cart

import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.harganow.data.repository.CartRepository
import com.example.harganow.data.repository.PremiseRepository
import com.example.harganow.domain.model.ItemPrice
import kotlinx.coroutines.launch

class CartViewModel(chosenList: MutableList<Map<ItemPrice, Int>>) : ViewModel() {
    private val cartRepository: CartRepository = CartRepository()
    private val premiseRepository: PremiseRepository = PremiseRepository()

    //    val cartItemIdList: List<Int>? = userRepository.getUserCartItem()
//    val cartItemList: ArrayList<Item> = ArrayList<Item>()
//    val cartItemPriceList: ArrayList<ItemPrice> = ArrayList<ItemPrice>()
    val TAG = "CartViewModel"
    var chosenList: MutableList<Map<ItemPrice, Int>> = chosenList

    var loading = mutableStateOf(false)
    val premiseId: String = premiseRepository.premiseId.value
    var hasData = mutableStateOf(true)
    var data: MutableList<Map<ItemPrice, Int>> = mutableListOf()
    var price: Double = 0.0

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            loading.value = true
            try {
                data = cartRepository.getCartData(premiseId)
            } catch (e: Exception) {
                hasData.value = false
                loading.value = false
                Log.d(TAG, "getData: $e")
                return@launch
            }
            getPrice()
            loading.value = false
        }
    }

    private fun getPrice() {
        var totalPrice: Double = 0.0
        for (item in data) {
            for (itemPrice in item.keys) {
                totalPrice += itemPrice.price * item[itemPrice]!!
            }
        }
        price = totalPrice
    }


    fun removeFromCart() {
        viewModelScope.launch {
            loading.value = true
            try {
                cartRepository.removeFromCart(premiseId, chosenList)
            } catch (e: Exception) {
                Log.d(TAG, "removeFromCart: $e")
                loading.value = false
                return@launch
            }
            Log.d(TAG , "removeFromCart: success")
            loading.value = false
        }
    }
}