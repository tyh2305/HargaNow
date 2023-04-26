package com.example.harganow.domain.model

import ItemRepository
import com.example.harganow.data.repository.PremiseRepository
import com.google.firebase.firestore.DocumentId

data class ItemPriceData(
    @DocumentId
    var id: String?,
    var item_code: String?,
    var premise_code: String?,
    var price: String?,
    var date: String?,
) {
    constructor() : this(null, null, null, null, null)

    suspend fun toItemPrice(
        itemRepository: ItemRepository,
        premiseRepository: PremiseRepository
    ): ItemPrice {
//        Get item with code
        var item = itemRepository.getItemWithId(item_code!!)
//        Get premise with code
        var premise = premiseRepository.getPremiseWithId(premise_code!!)
        return ItemPrice(
            item.data!!,
            premise.data!!,
            price!!.toDouble(),
            ItemDate.fromString(date!!)!!
        )
    }
}


data class ItemPrice(
    var item: Item,
    var premise: Premise,
    var price: Double,
    var date: ItemDate,
) {
    constructor() : this(Item(), Premise(), 0.0, ItemDate())
}
