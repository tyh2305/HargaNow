package com.example.harganow.presentation.cart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ItemRepository
import com.example.harganow.data.repository.UserRepository
import com.example.harganow.domain.model.Item
import com.example.harganow.domain.model.ItemDate
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.domain.model.Premise
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel : ViewModel() {
    private val itemRepository: ItemRepository = ItemRepository()
    private val userRepository: UserRepository = UserRepository()
    val cartItemIdList: List<Int>? = userRepository.getUserCartItem()
    val cartItemList: List<Item> = listOf<Item>()
    val cartItemPriceList: List<ItemPrice> = listOf<ItemPrice>()

    fun fetchItem() {
        if (cartItemIdList != null) {
            for (itemId in cartItemIdList) {
                cartItemList.plus(itemRepository.getItemWithId(itemId))
            }
        }
    }

    fun fetchItemPrice() {
        val date: ItemDate = ItemDate(18, 4, 2023)
        val premise: Premise = Premise(
            id = "1",
            name = "Tesco",
            type = Premise.Companion.Place.PASAR_RAYA,
            district = "Wangsa Maju",
            state = Premise.Companion.State.KUALA_LUMPUR,
        )
        val price: Double = 19.90

        if (cartItemIdList != null) {
            for (item in cartItemList) {
//                cartItemPriceList.plus(itemRepository.getItemPriceWithId(itemId))
                cartItemPriceList.plus(
                    ItemPrice(
                        price = price,
                        date = date,
                        item = item,
                        premise = premise
                    )
                )
            }
        }
    }

}