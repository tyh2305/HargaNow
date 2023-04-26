package com.example.harganow.presentation.cart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ItemRepository
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewModelScope
import com.example.harganow.data.repository.CartRepository
import com.example.harganow.data.repository.UserRepository
import com.example.harganow.domain.model.DataOrException
import com.example.harganow.domain.model.Item
import com.example.harganow.domain.model.ItemDate
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.domain.model.Place
import com.example.harganow.domain.model.Premise
import com.example.harganow.domain.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val itemRepository: ItemRepository = ItemRepository()
    private val userRepository: UserRepository = UserRepository()
    private val cartRepository: CartRepository = CartRepository()

    //    val cartItemIdList: List<Int>? = userRepository.getUserCartItem()
//    val cartItemList: ArrayList<Item> = ArrayList<Item>()
//    val cartItemPriceList: ArrayList<ItemPrice> = ArrayList<ItemPrice>()
    val TAG = "CartViewModel"

    var loading = mutableStateOf(false)
    val premiseId: String = "18098"
    var data: MutableList<Map<ItemPrice, Int>> = mutableListOf()
    var price: Double = 0.0

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            loading.value = true
            data = cartRepository.getCartData(premiseId)
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

}