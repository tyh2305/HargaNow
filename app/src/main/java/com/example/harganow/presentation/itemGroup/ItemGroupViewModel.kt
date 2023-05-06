package com.example.harganow.presentation.itemGroup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harganow.data.repository.PriceRepository
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.utils.NavInfo
import kotlinx.coroutines.launch

class ItemGroupViewModel(): ViewModel() {

    val itemGroup = NavInfo.itemGroup

    val tag = "ItemGroupViewModel"
    var loading = mutableStateOf(false)

    var itemCategoryList: List<String> = listOf()

    var itemWithPriceList: List<ItemPrice> = listOf()

    init{
        getData()
    }

    private fun getData(){
        viewModelScope.launch {
            loading.value = true

            for(item in PriceRepository.itemWithLatestPriceList){
                if(item.item.item_group == itemGroup){
                    itemWithPriceList += item
                }
            }

            for(item in itemWithPriceList){
                if(!itemCategoryList.contains(item.item.item_category)){
                    itemCategoryList += item.item.item_category!!
                }
            }

            loading.value = false
        }
    }
}