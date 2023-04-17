package com.example.harganow.cart

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CartScreen(
    state: CartState = CartState(),
    actions: CartActions = CartActions()
) {
    // TODO UI Logic
}

@Composable
@Preview(name = "Cart")
private fun CartScreenPreview() {
    CartScreen()
}

