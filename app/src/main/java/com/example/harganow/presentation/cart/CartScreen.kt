package com.example.harganow.presentation.cart

import android.util.Log
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.presentation.cart.components.CartItemListBuilder
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

    Surface(
        color = MaterialTheme.colors.background
    ) {
        Text(text = "Cart Screen")
        CircularProgressBar(
            isDisplayed = cartViewModel.loading.value
        )
        CartItemListBuilder(cartViewModel.data)
    }
}