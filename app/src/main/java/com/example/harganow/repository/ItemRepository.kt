package com.example.harganow.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.harganow.dao.ItemDAO
import com.example.harganow.model.Item

class ItemRepository(private val itemDAO: ItemDAO) {

    val allItem: LiveData<List<Item>> = itemDAO.getAllItem()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun add(item: Item) {
        itemDAO.insert(item)
    }

    @WorkerThread
    suspend fun update(item: Item) {
        itemDAO.update(item)
    }

    @WorkerThread
    suspend fun delete(item: Item) {
        itemDAO.delete(item)
    }

    companion object {
        private const val TAG = "ItemRepository"
    }
}


