package com.example.harganow.domain.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Order(
    @DocumentId
    var id: String?,
    var userId: String?,
    @ServerTimestamp
    var timestamp: Date?,
    var items: List<CartItem>,
    var address: Address,
    var amount: Double,
    var paymentMethod: String,
) {
    constructor() : this(null, null, null, listOf(), Address(), 0.0, "")
}

