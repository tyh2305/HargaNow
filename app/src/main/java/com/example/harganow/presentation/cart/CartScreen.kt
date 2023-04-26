package com.example.harganow.presentation.cart

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.presentation.cart.components.CartItemListBuilder
import com.example.harganow.ui.theme.Orange
//import com.example.harganow.presentation.cart.components.CartItemListBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Composable
fun CircularProgressBar(isDisplayed: Boolean) {
    if (isDisplayed) {
        CircularProgressIndicator()
    }
}


@Composable
fun CartScreen(navigateToPreviousStack: () -> Unit, navigateToCheckout: () -> Unit) {
    val TAG = "CartScreen"
    val context = LocalContext.current
    val cartViewModel: CartViewModel = CartViewModel()
    var chosenList: MutableList<Map<ItemPrice, Int>> = remember { mutableListOf() }

    fun handleCheckout() {
        chosenList.forEach { item ->
            Log.d("Chosen Item List", item.toString())
        }
//        navigateToCheckout()
    }

    Surface(
        color = MaterialTheme.colors.background
    ) {
        Text(text = "Cart Screen")
        CircularProgressBar(
            isDisplayed = cartViewModel.loading.value
        )
        CartItemListBuilder(
            itemList = cartViewModel.data,
            selectedItem = chosenList
        )
        Box(
            contentAlignment = Alignment.BottomCenter,
        ) {
            Button(onClick = { handleCheckout() }) {
                Text(text = "Checkout")
            }
        }
    }
}