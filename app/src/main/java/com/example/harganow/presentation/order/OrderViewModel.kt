package com.example.harganow.presentation.order

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harganow.data.auth.FireAuthRepository
import com.example.harganow.data.repository.OrderRepository
import com.example.harganow.data.repository.PriceRepository
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.domain.model.Order
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    private val authRepository = FireAuthRepository()
    private val priceRepository = PriceRepository.itemWithLatestPriceList
    val currentUser = authRepository.getCurrentUser()
    private val orderRepository = OrderRepository()

    val TAG = "OrderViewModel"
    val loading = mutableStateOf(false)
    var data: List<Order> = listOf()

    init {
        getData()
    }

    fun getItemWithId(id: Int?): ItemPrice? {
        for (item in priceRepository) {
            if (item.item.id == id.toString()) {
                return item
            }
        }
        return null
    }

    private fun getData() {
        viewModelScope.launch {
            loading.value = true
            var doe = orderRepository.getUserOrders(currentUser!!.uid)!!
            if (doe.data != null) {
                data = doe.data!!
            } else {
                Log.e(TAG, doe.exception.toString())
            }
            loading.value = false
        }
    }

    private fun reOrder(order: Order) {
        viewModelScope.launch {
            // TODO handle reorder case
        }
    }

}