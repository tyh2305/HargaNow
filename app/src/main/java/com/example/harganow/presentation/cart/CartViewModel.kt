package com.example.harganow.presentation.cart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ItemRepository
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
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

//class CartViewModel : ViewModel() {
//    private val itemRepository: ItemRepository = ItemRepository()
//    private val userRepository: UserRepository = UserRepository()
//    val cartItemIdList: List<Int>? = userRepository.getUserCartItem()
//    val cartItemList: ArrayList<Item> = ArrayList<Item>()
//    val cartItemPriceList: ArrayList<ItemPrice> = ArrayList<ItemPrice>()
//    val TAG = "CartViewModel"
//
//    var loading = mutableStateOf(false)
//    val data: MutableState<DataOrException<List<ItemPrice>, Exception>> = mutableStateOf(
//        DataOrException(
//            listOf<ItemPrice>(), Exception()
//        )
//    )
//
//    init {
//        getData()
//    }
//
//    private fun getData() {
//        viewModelScope.launch {
//            loading.value = true
//            if (cartItemIdList != null) {
//                for (itemId in cartItemIdList) {
//                    Log.d(TAG, "fetchItem: $itemId")
//                    var doe = itemRepository.getItemWithId(itemId)
//                    Log.d(TAG, "fetchItemDone: ${doe.data}")
//                    cartItemList.add(doe.data!!)
//                    Log.d(TAG, "addToList: ${cartItemList.size}")
//                }
//            }
//            fetchItemPrice()
//            loading.value = false
//        }
//    }
//
//    suspend fun fetchItem() {
//        if (cartItemIdList != null) {
//            for (itemId in cartItemIdList) {
//                Log.d(TAG, "fetchItem: $itemId")
////                cartItemList.add(itemRepository.getItemWithId(itemId))
//                Log.d(TAG, "fetchItemDone: ${cartItemList.size}")
//            }
//        }
//    }
//
//    fun fetchItemPrice() {
//        val date: ItemDate = ItemDate(18, 4, 2023)
//        val premise: Premise = Premise(
//            id = "1",
//            name = "Tesco",
//            type = Place.PASAR_RAYA.toString(),
//            district = "Wangsa Maju",
//            state = State.KUALA_LUMPUR.toString(),
//        )
//        val price: Double = 19.90
//
//        if (cartItemIdList != null) {
//            for (item in cartItemList) {
////                cartItemPriceList.plus(itemRepository.getItemPriceWithId(itemId))
//                cartItemPriceList.add(
//                    ItemPrice(
//                        price = price,
//                        date = date,
//                        item = item,
//                        premise = premise
//                    )
//                )
//            }
//        } else {
//            Log.d(TAG, "fetchItemPrice: cartItemIdList is null")
//        }
//    }
//
//}