package com.example.harganow.data.source

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Firestore {

    companion object {
        val db = Firebase.firestore
        fun getCollection(collection: String) = db.collection(collection)
        fun getDocument(collection: String, document: String) =
            db.collection(collection).document(document)

        fun DocRef(collectionName: String, id: Int) =
            Firestore.getCollection(collectionName).document(id.toString())

        fun ColRef(collectionName: String) = Firestore.getCollection(collectionName)

        fun ColRefFilter(collectionName: String, field: String, value: String) =
            Firestore.getCollection(collectionName).whereEqualTo(field, value)
    }
}