package com.example.harganow.presentation.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


/**
 * UI State that represents CartScreen
 **/
class CartState

/**
 * Cart Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class CartActions(
    val onClick: () -> Unit = {}
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalCartActions = staticCompositionLocalOf<CartActions> {
    error("{NAME} Actions Were not provided, make sure ProvideCartActions is called")
}

@Composable
fun ProvideCartActions(actions: CartActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalCartActions provides actions) {
        content.invoke()
    }
}

