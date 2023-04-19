package com.example.harganow.domain.model

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

