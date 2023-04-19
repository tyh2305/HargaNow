package com.example.harganow.domain.model

import com.example.harganow.utils.ImageGetter
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class Item(
    @DocumentId var id: String,
    @PropertyName("item") val name: String,
    @PropertyName("unit") val unit: String,
    @PropertyName("item_category") val category: String,
    @PropertyName("item_group") val group: String,
)

