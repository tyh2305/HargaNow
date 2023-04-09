package com.example.harganow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.harganow.dao.ItemFirestoreDAO
import com.example.harganow.model.Item
import com.example.harganow.repository.ItemRepository
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    var itemList: LiveData<List<Item>>
    private val repository: ItemRepository

    init {
        val itemDAO = ItemFirestoreDAO()
        repository = ItemRepository(itemDAO)
        itemList = repository.allItem
    }

    fun addItem(item: Item) = viewModelScope.launch {
        repository.add(item)
    }

    fun updateItem(item: Item) = viewModelScope.launch {
        repository.update(item)
    }

    fun deleteItem(item: Item) = viewModelScope.launch {
        repository.delete(item)
    }
}