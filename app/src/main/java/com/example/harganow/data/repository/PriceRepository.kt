package com.example.harganow.data.repository

import android.util.Log
import com.example.harganow.data.source.Firestore
import com.example.harganow.domain.model.DataOrException
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.domain.model.ItemPriceData
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

object PriceRepository {
    val TAG = "PriceRepository"
    val collectionName = "price"
    var itemWithLatestPriceList: MutableList<ItemPrice> = mutableListOf()
    var itemWithAllPriceMap: MutableMap<String, MutableList<ItemPrice>> = mutableMapOf()
    var itemLoaded = false
    var i = 0


    suspend fun getPriceWithPremise(premiseId: String): DataOrException<List<ItemPriceData>, Exception> {
        val dataOrException = DataOrException<List<ItemPriceData>, Exception>()
        try {
            dataOrException.data =
                Firestore.ColRefFilter(collectionName, "premise_code", premiseId).get().await()
                    .map { document ->
                        document.toObject(ItemPriceData::class.java)
                    }
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        return dataOrException
    }

    suspend fun getLatestPriceWithPremiseWithLimit(premiseId: String): DataOrException<List<ItemPriceData>, Exception> {
        val dataOrException = DataOrException<List<ItemPriceData>, Exception>()
        try {
            dataOrException.data =
                Firestore.ColRefFilter(collectionName, "premise_code", premiseId)
                    .whereGreaterThan("date", "1970-01-01")
                    .orderBy("date", Query.Direction.DESCENDING)
                    .limit(50).get().await()
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
                Firestore.ColRefFilter(collectionName, "premise_code", premiseId)
                    .whereEqualTo("item_code", itemId)
                    .whereGreaterThan("date", "1970-01-01")
                    .orderBy("date")
                    .limit(15).get().await() // Alter limit if more date is needed
                    .map { document ->
                        document.toObject(ItemPriceData::class.java)
                    }
            if(dataOrException.data!!.isEmpty()) {
                throw FirebaseFirestoreException("No data found", FirebaseFirestoreException.Code.NOT_FOUND)
            }
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        i++
        Log.d(TAG, "getLatestPriceWithPremiseAndItem: $i")
        return dataOrException
    }

    suspend fun getLatestPriceWithPremiseAndItem(
        premiseId: String,
        itemId: String
    ): DataOrException<List<ItemPriceData>, Exception> {
        val dataOrException = DataOrException<List<ItemPriceData>, Exception>()
        try {
            dataOrException.data =
                Firestore.ColRefFilter(collectionName, "premise_code", premiseId)
                    .whereEqualTo("item_code", itemId)
                    .whereGreaterThan("date", "1970-01-01")
                    .orderBy("date", Query.Direction.DESCENDING)
                    .limit(10).get().await()
                    .map { document ->
                        document.toObject(ItemPriceData::class.java)
                    }
            if(dataOrException.data!!.isEmpty()) {
                throw FirebaseFirestoreException("No data found", FirebaseFirestoreException.Code.NOT_FOUND)
            }
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        i++
        Log.d(TAG, "getLatestPriceWithPremiseAndItem: $i")
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

    suspend fun getTotalPrice(
        premiseId: String,
        items: List<Map<Int, Int>>
    ): DataOrException<Double, Exception> {
        val dataOrException = DataOrException<Double, Exception>()
        var totalPrice = 0.0
        try {
            for (item in items) {
                val itemPrice = getPriceWithPremiseAndItem(premiseId, item.keys.first().toString())
                if (itemPrice.data == null) {
                    throw Exception(itemPrice.exception)
                }
                totalPrice += (itemPrice.data as ItemPriceData).price!!.toDouble() * item.values.first()
            }
            dataOrException.data = totalPrice
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        return dataOrException

    }
}