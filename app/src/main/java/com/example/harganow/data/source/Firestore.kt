package com.example.harganow.data.source

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Firestore {

    companion object {
        val db = Firebase.firestore
        fun getCollection(collection: String) = db.collection(collection)
        fun getDocument(collection: String, document: String) =
            db.collection(collection).document(document)
    }
}