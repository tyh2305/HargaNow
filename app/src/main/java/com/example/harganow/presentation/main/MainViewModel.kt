package com.example.harganow.presentation.main

import ItemRepository
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harganow.data.repository.PremiseRepository
import com.example.harganow.data.repository.PriceRepository
import com.example.harganow.domain.model.DataOrException
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.domain.model.ItemPriceData
import com.example.harganow.domain.model.Premise
import kotlinx.coroutines.launch

class MainViewModel(
    private val loadingFinUpdate: (Boolean) -> Unit
) : ViewModel() {

    private val itemRepository: ItemRepository = ItemRepository()
    private val premiseRepository: PremiseRepository = PremiseRepository()

    val tag = "HomeViewModel"

    var loading = mutableStateOf(false)
    private lateinit var premiseId: String

    var premiseName: String = ""
    var tempPremiseName: String = ""

    private fun getData() {
        viewModelScope.launch {
            loading.value = true

            if(!PriceRepository.itemLoaded){
                var itemWithPriceDataList: List<ItemPriceData> = listOf()

                var itemWithPriceDataListDoe: DataOrException<List<ItemPriceData>, Exception> =
                    PriceRepository.getLatestPriceWithPremiseWithLimit(premiseId)

                if(itemWithPriceDataListDoe.exception == null){
                    itemWithPriceDataList = itemWithPriceDataListDoe.data!!
                }

                val temp: MutableList<ItemPriceData> = mutableListOf()
                for(itemWithPriceData in itemWithPriceDataList){
                    if (temp.isEmpty() || temp.none { it.item_code == itemWithPriceData.item_code }){
                        temp.add(itemWithPriceData)
                    }
                }

                PriceRepository.itemWithLatestPriceList = temp.map { it.toItemPrice(itemRepository, premiseRepository) } as MutableList<ItemPrice>

                temp.clear()
                for (itemWithPrice in PriceRepository.itemWithLatestPriceList){
                    itemWithPriceDataListDoe = PriceRepository.getPriceWithPremiseAndItem(premiseId, itemWithPrice.item.id!!)

                    if(itemWithPriceDataListDoe.exception == null){
                        itemWithPriceDataList = itemWithPriceDataListDoe.data!!
                    }

                    if(!PriceRepository.itemWithAllPriceMap.containsKey(itemWithPrice.item.id)){
                        PriceRepository.itemWithAllPriceMap[itemWithPrice.item.id!!] = itemWithPriceDataList.map { it.toItemPrice(itemRepository, premiseRepository) } as MutableList<ItemPrice>
                    }

                }

                PriceRepository.itemLoaded = true
            }

            loading.value = false
            if(!loading.value){
                loadingFinUpdate(true)
            }
        }
    }

    fun getPremiseData(){
        if(premiseName == ""){
            return
        }

        viewModelScope.launch {
            if(tempPremiseName == premiseName){
                return@launch
            }
            loading.value = true
            if(loading.value){
               loadingFinUpdate(false)
            }

            val premiseDataDoe = premiseRepository.getPremiseWithName(premiseName)
            var premiseList = listOf<Premise>()

            if(premiseDataDoe.exception == null){
                premiseList = premiseDataDoe.data!!
            }

            premiseId = premiseList[0].id
            premiseName = premiseList[0].premise
            tempPremiseName = premiseName
            PriceRepository.itemLoaded = false
            PriceRepository.itemWithLatestPriceList.clear()
            PriceRepository.itemWithAllPriceMap.clear()


            getData()

            loading.value = false

        }
    }
}