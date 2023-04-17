package com.example.harganow.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CartRoute(
    coordinator: CartCoordinator = rememberCartCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(CartState())

    // UI Actions
    val actions = rememberCartActions(coordinator)

    // UI Rendering
    CartScreen(uiState, actions)
}


@Composable
fun rememberCartActions(coordinator: CartCoordinator): CartActions {
    return remember(coordinator) {
        CartActions(
            onClick = coordinator::doStuff
        )
    }
}