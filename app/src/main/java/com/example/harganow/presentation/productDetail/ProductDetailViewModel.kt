package com.example.harganow.presentation.productDetail

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harganow.data.repository.CartRepository
import com.example.harganow.data.repository.PriceRepository
import com.example.harganow.domain.model.CartItem
import com.example.harganow.domain.model.ItemPrice
import kotlinx.coroutines.launch



class ProductDetailViewModel(
    itemId: String,

): ViewModel() {
    val tag = "ProductDetailViewModel"
    var loading = mutableStateOf(false)

    private val cartRepository: CartRepository = CartRepository()

    private val itemId: String = itemId

    var alternativeItemWithPriceList: List<ItemPrice> = listOf()

    var itemWithPrice:ItemPrice = ItemPrice()

    init{
        getData()
    }

    private fun getData(){
        viewModelScope.launch{
            loading.value = true


            for(item in PriceRepository.itemWithLatestPriceList){
                if(item.item.id == itemId){
                    itemWithPrice = item
                    break
                }
            }

            for(item in PriceRepository.itemWithLatestPriceList){
                if(item.item.item_category == itemWithPrice.item.item_category && item.item.id != itemWithPrice.item.id){
                    alternativeItemWithPriceList += item
                }
            }

            loading.value = false
        }
    }

    fun handleAddToCart(quantity: Int){
        viewModelScope.launch{

            val cartItem = CartItem(itemWithPrice.item.id!!.toInt(), quantity)

            Log.v(tag, "cartItem: $cartItem")

            // TODO: enable this when addToCart is usable
            //cartRepository.addToCart(itemWithPrice.premise.id, cartItem)
        }
    }

}
