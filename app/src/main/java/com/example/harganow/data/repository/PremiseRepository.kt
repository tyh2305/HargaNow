package com.example.harganow.data.repository

import android.util.Log
import com.example.harganow.data.source.Firestore
import com.example.harganow.domain.model.DataOrException
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.domain.model.Premise
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class PremiseRepository {
    val TAG = "PremiseRepository"
    val collectionName = "premise"

    suspend fun getAllPremise(): DataOrException<List<Premise>, Exception> {
        val dataOrException = DataOrException<List<Premise>, Exception>()
        try {
            dataOrException.data =
                Firestore.ColRef(collectionName).get().await()
                    .map { document ->
                        document.toObject(Premise::class.java)
                    }
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        return dataOrException
    }

    //    getPremise with district, type, state
    suspend fun getPremiseWithDistrict(district: String): DataOrException<List<Premise>, Exception> {
        val dataOrException = DataOrException<List<Premise>, Exception>()
        try {
            dataOrException.data =
                Firestore.ColRefFilter(collectionName, "district", district).get().await()
                    .map { document ->
                        document.toObject(Premise::class.java)
                    }
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        return dataOrException
    }

    suspend fun getPremiseWithType(type: String): DataOrException<List<Premise>, Exception> {
        val dataOrException = DataOrException<List<Premise>, Exception>()
        try {
            dataOrException.data =
                Firestore.ColRefFilter(collectionName, "premise_type", type).get().await()
                    .map { document ->
                        document.toObject(Premise::class.java)
                    }
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        return dataOrException
    }

    suspend fun getPremiseWithState(state: String): DataOrException<List<Premise>, Exception> {
        val dataOrException = DataOrException<List<Premise>, Exception>()
        try {
            dataOrException.data =
                Firestore.ColRefFilter(collectionName, "state", state).get().await()
                    .map { document ->
                        document.toObject(Premise::class.java)
                    }
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        return dataOrException
    }

    suspend fun getPremiseWithId(id: String): DataOrException<Premise, Exception> {
        val dataOrException = DataOrException<Premise, Exception>()
        try {
            dataOrException.data =
                Firestore.DocRef(collectionName, id).get().await()
                    .toObject(Premise::class.java)
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        return dataOrException
    }
}