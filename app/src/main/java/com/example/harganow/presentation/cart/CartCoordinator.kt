package com.example.harganow.presentation.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class CartCoordinator(
    val viewModel: CartViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    fun doStuff() {
        // TODO Handle UI Action
    }
}

@Composable
fun rememberCartCoordinator(
    viewModel: CartViewModel = hiltViewModel()
): CartCoordinator {
    return remember(viewModel) {
        CartCoordinator(
            viewModel = viewModel
        )
    }
}