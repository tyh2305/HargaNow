package com.example.harganow.presentation.cart

import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.presentation.cart.components.CartItemListBuilder

@Composable
fun CartScreen() {
    val TAG = "CartScreen"
    val context = LocalContext.current
    val cartViewModel: CartViewModel = CartViewModel()

    cartViewModel.fetchItem()
    cartViewModel.fetchItemPrice()

    val cartPriceList: List<ItemPrice>? = cartViewModel.cartItemPriceList
    Log.d(TAG, "CartScreen: $cartPriceList")

    Surface(
        color = MaterialTheme.colors.background
    ) {
        Text(text = "Cart Screen")
        CartItemListBuilder(itemPriceList = cartPriceList!!)
    }
}