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
    private val loadingFinUpdate: () -> Unit
) : ViewModel() {

    private val itemRepository: ItemRepository = ItemRepository()
    private val premiseRepository: PremiseRepository = PremiseRepository()

    val tag = "HomeViewModel"

    var loading = mutableStateOf(false)
    private lateinit var premiseId: String

    var premiseName: String = ""

    val itemGroupNames = listOf(
        "BARANGAN SEGAR",
        "MAKANAN SIAP MASAK",
        "MINUMAN",
        "BARANGAN KEDAI SERBENEKA",
        "BARANGAN KERING",
        "PRODUK KEBERSIHAN",
        "SUSU DAN BARANGAN BAYI",
        "BARANGAN BERBUNGKUS"
    )

    // For Demo Purposes TODO: uncomment commented out items
    private val itemIds = listOf<String>(
        "1513", // SUSU DAN BARANGAN BAYI
        "1514",
        "2010",
        "2009",
        "1904",
        "1551", // BARANGAN SEGAR
        "1552",
        "47",
        "845",
        "113",
        "160", // BARANGAN KERING
        "129",
        "1440",
        "1613", // BARANGAN BERBUNGKUS
        "1590",
        "190",
        "191",
        "192",
        "272",
        "1494",
        "1976",
        "1978",
    )

    private fun getData() {
        viewModelScope.launch {
            loading.value = true

                // Have Time get all about 4k items

            if(!PriceRepository.itemLoaded){
                var itemWithPriceDataListDoe: DataOrException<List<ItemPriceData>, Exception>
                var itemWithPriceDataList: List<ItemPriceData>
                var itemWithPriceList: List<ItemPrice>

                for(itemId in itemIds){
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

            loading.value = false
            if(!loading.value){
                loadingFinUpdate()
            }
        }
    }

    fun getPremiseData(){
        if(premiseName == ""){
            return
        }

        viewModelScope.launch {
            loading.value = true

            val premiseDataDoe = premiseRepository.getPremiseWithName(premiseName)
            var premiseList = listOf<Premise>()

            if(premiseDataDoe.exception == null){
                premiseList = premiseDataDoe.data!!
            }

            premiseId = premiseList[0].id
            premiseName = premiseList[0].premise

            getData()

            loading.value = false
        }
    }

}