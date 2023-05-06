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
//    loadingFinUpdate: () -> Unit
) : ViewModel() {
//    var loadingFinUpdate = loadingFinUpdate

    private val itemRepository: ItemRepository = ItemRepository()
    private val premiseRepository: PremiseRepository = PremiseRepository()

    val tag = "HomeViewModel"

    var loading = mutableStateOf(false)
    private val premiseId: String = "18098"

    //TODO: implement
    var premiseName: String = ""
    var premise: Premise = Premise()


    private val itemGroupNames = listOf(
        "BARANGAN SEGAR",
        "MAKANAN SIAP MASAK",
        "MINUMAN",
        "BARANGAN KEDAI SERBENEKA",
        "BARANGAN KERING",
        "PRODUK KEBERSIHAN",
        "SUSU DAN BARANGAN BAYI",
        "BARANGAN BERBUNGKUS"
    )

    // For Demo Purposes TODO: remove commented out items
    private val itemIds = listOf<String>(
        "95",
        "88",
        "850",
        "847",
        "845",
//        "1944",
//        "1943",
//        "160",
//        "1564",
//        "1442",
//        "2015",
//        "2014",
//        "2010",
//        "2009",
//        "1975",
//        "992",
//        "957",
//        "919",
//        "918",
//        "917"
    )

//    var itemWithPriceListDoe = DataOrException<List<ItemPriceData>, Exception>()

    val itemGroupItemWithPriceMap = LinkedHashMap<String, List<ItemPrice>>()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            loading.value = true
            if(!PriceRepository.itemLoaded){
                var itemWithPriceDataListDoe: DataOrException<List<ItemPriceData>, Exception>
                var itemWithPriceDataList: List<ItemPriceData>
                var itemWithPriceList: List<ItemPrice>

                // TODO: change to use premise.id
                for(itemId in itemIds){
                    // TODO: change to use premise.id
                    itemWithPriceDataListDoe = PriceRepository.getLatestPriceWithPremiseAndItem(premiseId,itemId)
                    if(itemWithPriceDataListDoe.exception == null){
                        itemWithPriceDataList = itemWithPriceDataListDoe.data!!

                        // Convert ItemPriceData to ItemPrice
                        itemWithPriceList = itemWithPriceDataList.map { it.toItemPrice(itemRepository, premiseRepository) }


                        if(PriceRepository.itemWithLatestPriceList.isEmpty()){
                            PriceRepository.itemWithLatestPriceList.add(itemWithPriceList[0])
                        } else if (PriceRepository.itemWithLatestPriceList.last().item.id != itemWithPriceList[0].item.id){
                            PriceRepository.itemWithLatestPriceList.add(itemWithPriceList[0])
                        }

                        PriceRepository.itemWithAllPriceMap[itemId] = itemWithPriceList.reversed()
                    }
                }

                PriceRepository.itemLoaded = true
            }

            val itemWithPrice = PriceRepository.itemWithLatestPriceList

            for (i in itemGroupNames.indices){
                itemGroupItemWithPriceMap[itemGroupNames[i]] = itemWithPrice.filter { it.item.item_group == itemGroupNames[i] }
            }

            //Remove empty list from itemGroupItemWithPriceMap
            itemGroupItemWithPriceMap.entries.removeIf { it.value.isEmpty() }

            loading.value = false
        }
//        loadingFinUpdate()
    }

//    fun getPremiseData(){
//        if(premiseName == ""){
//            return
//        }
//
//        viewModelScope.launch {
//            loading.value = true
//
//            val premiseDataDoe = premiseRepository.getPremiseWithName(premiseName)
//            var premiseList = listOf<Premise>()
//
//            if(premiseDataDoe.exception == null){
//                premiseList = premiseDataDoe.data!!
//            }
//
//            premise = premiseList[0]
//
//            getData()
//
//            loading.value = false
//        }
//    }

}