package com.example.harganow.domain.model

import com.google.firebase.firestore.DocumentId

data class Cart(
    @DocumentId
    var id: String?,
    var items: List<Map<Int, Int>>?,
) {
    constructor() : this(null, null)
}
