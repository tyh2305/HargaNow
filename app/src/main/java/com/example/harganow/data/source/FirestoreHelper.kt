package com.example.harganow.data.source

import com.google.firebase.firestore.FirebaseFirestore

class FirestoreHelper {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    fun getCollection(collection: String) = firestore.collection(collection)
    fun getDocument(collection: String, document: String) = firestore.collection(collection).document(document)

    companion object {
        @Volatile
        private var INSTANCE: FirestoreHelper? = null

        fun getInstance(): FirestoreHelper {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = FirestoreHelper()
                INSTANCE = instance
                return instance
            }
        }
    }


}