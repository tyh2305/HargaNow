package com.example.harganow.domain.model

import com.example.harganow.R
import com.google.firebase.firestore.DocumentId

class Item(
    @DocumentId
    var id: String? = null,
    var item: String? = null,
    var unit: String? = null,
    var item_category: String? = null,
    var item_group: String? = null,
) {
    constructor() : this(null, null, null, null, null)
}

fun convertToItemGroupNameId(itemGroupName: String): Int{
    var itemGroupNameId = 0

    when(itemGroupName){
        "BARANGAN SEGAR" -> itemGroupNameId = R.string.fresh_goods
        "BARANGAN KERING" -> itemGroupNameId = R.string.dry_goods
        "BARANGAN BERBUNGKUS" -> itemGroupNameId = R.string.packaged_goods
        "MINUMAN" -> itemGroupNameId = R.string.beverages
        "SUSU DAN BARANGAN BAYI" -> itemGroupNameId = R.string.milk_and_baby_products
        "PRODUK KEBERSIHAN" -> itemGroupNameId = R.string.cleaning_products
    }

    return itemGroupNameId
}