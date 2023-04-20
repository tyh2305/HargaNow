package com.example.harganow.data.repository

import android.util.Log
import com.example.harganow.data.source.Firestore
import com.example.harganow.domain.model.DataOrException
import com.example.harganow.domain.model.Item
import com.example.harganow.domain.model.ItemPriceData
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class PriceRepository {
    val TAG = "PriceRepository"
    val collectionName = "price"

    suspend fun getPriceWithPremise(premiseId: String): DataOrException<List<ItemPriceData>, Exception> {
        val dataOrException = DataOrException<List<ItemPriceData>, Exception>()
        try {
            dataOrException.data =
                Firestore.ColRefFilter(collectionName, "premise", premiseId).get().await()
                    .map { document ->
                        document.toObject(ItemPriceData::class.java)
                    }
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        return dataOrException
    }

    suspend fun getPriceWithItem(itemId: String): DataOrException<List<ItemPriceData>, Exception> {
        val dataOrException = DataOrException<List<ItemPriceData>, Exception>()
        try {
            dataOrException.data =
                Firestore.ColRefFilter(collectionName, "premise", itemId).get().await()
                    .map { document ->
                        document.toObject(ItemPriceData::class.java)
                    }
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        return dataOrException
    }

    suspend fun getPriceWithPremiseAndItem(
        premiseId: String,
        itemId: String
    ): DataOrException<List<ItemPriceData>, Exception> {
        val dataOrException = DataOrException<List<ItemPriceData>, Exception>()
        try {
            dataOrException.data =
                Firestore.ColRefFilter(collectionName, "premise", premiseId)
                    .whereEqualTo("item", itemId).get().await()
                    .map { document ->
                        document.toObject(ItemPriceData::class.java)
                    }
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        return dataOrException
    }

    suspend fun getPriceWithDateRange(
        startDate: String,
        endDate: String
    ): DataOrException<List<ItemPriceData>, Exception> {
        val dataOrException = DataOrException<List<ItemPriceData>, Exception>()
        try {
            dataOrException.data =
                Firestore.ColRef(collectionName)
                    .whereGreaterThanOrEqualTo("date", startDate)
                    .whereLessThanOrEqualTo("date", endDate).get().await()
                    .map { document ->
                        document.toObject(ItemPriceData::class.java)
                    }
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        return dataOrException
    }
}