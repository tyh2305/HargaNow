package com.example.harganow.domain.model

import com.google.firebase.firestore.DocumentId

data class Cart(
    @DocumentId
    var id: String?,
    var items: List<CartItem>?,
) {
    constructor() : this(null, null)
}

data class CartItem(
    var id: Int?,
    var count: Int?,
) {
    constructor() : this(null, null)
}