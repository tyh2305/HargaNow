package com.example.harganow.domain.model

import com.google.firebase.firestore.DocumentId

class Announcement {
    @DocumentId
    var id: String? = null
    var title: String? = null
    var description: String? = null
}