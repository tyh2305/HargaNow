package com.example.harganow.presentation.checkout

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.harganow.domain.model.ItemPrice

@Composable
fun CheckOutScreen(
    navigateToPreviousStack: () -> Unit,
    chosenList: MutableList<Map<ItemPrice, Int>>
) {
    val TAG = "CheckOutScreen"
    Log.d(TAG, "CheckOutScreen: chosenList: $chosenList")
    var cloneList = mutableListOf<Map<ItemPrice, Int>>()
    cloneList.addAll(chosenList)

    CheckOutBuilder(
        itemList = cloneList,
        navigateToOrder = { /*TODO*/ },
        navigateToPreviousStack = navigateToPreviousStack
    )
}