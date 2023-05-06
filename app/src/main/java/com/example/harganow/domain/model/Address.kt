package com.example.harganow.domain.model

import com.google.firebase.firestore.DocumentId

data class Address(
    @DocumentId
    var id: String?,
    var unitNumber: String?,
    var addressLine1: String?,
    var addressLine2: String?,
    var poscode: String?,
    var city: String?,
    var state: String?,
) {
    constructor() : this(null, null, null, null, null, null, null)
}

