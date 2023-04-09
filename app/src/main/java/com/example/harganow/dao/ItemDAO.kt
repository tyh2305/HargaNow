package com.example.harganow.dao

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.harganow.service.FirestoreHelper
import com.example.harganow.model.Item

interface ItemDAO {
    fun getAllItem(): LiveData<List<Item>>

    fun getItem(id: String): LiveData<Item>

    suspend fun insert(item: Item)

    suspend fun update(item: Item)

    suspend fun delete(item: Item)
}

class ItemFirestoreDAO : ItemDAO {
    private val firestoreHelper = FirestoreHelper.getInstance()
    private val itemCollection = firestoreHelper.getCollection("item")
    override fun getAllItem(): LiveData<List<Item>> {
        val itemsLiveData = MutableLiveData<List<Item>>()

        itemCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.w(TAG, "Listen failed", error)
                return@addSnapshotListener
            }

            val itemsList = mutableListOf<Item>()
            for (document in snapshot!!) {
                var item = document.toObject(Item::class.java)
                item.id = document.id
                itemsList.add(item)
            }

            itemsLiveData.value = itemsList
        }

        return itemsLiveData
    }

    override fun getItem(id: String): LiveData<Item> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(item: Item) {
        itemCollection.add(item)
    }

    override suspend fun update(item: Item) {
        itemCollection.document(item.id).set(item)
    }

    override suspend fun delete(item: Item) {
        itemCollection.document(item.id).delete()
    }

    companion object {
        private const val TAG = "ItemFirestoreDAO"
    }

}