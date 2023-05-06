package com.example.harganow.presentation.order

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.presentation.cart.CircularProgressBar
import com.example.harganow.ui.theme.Orange

@Composable
fun OrderScreen(
    navigateToPreviousStack: () -> Boolean,
    navigateToCheckOut: () -> Unit,
    chosenList: MutableList<Map<ItemPrice, Int>>
) {
    val TAG = "OrderScreen"
    val context = LocalContext.current
    val orderViewModel: OrderViewModel = OrderViewModel()

    fun handleReOrder() {
        navigateToCheckOut()
    }

    Surface(
        color = MaterialTheme.colors.background
    ) {
        Text(text = "Order Screen")
        CircularProgressBar(
            isDisplayed = orderViewModel.loading.value
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Orange)
                .padding(16.dp)
                .onGloballyPositioned {
                    Log.d(TAG, "CartScreen: onGloballyPositioned: $it")
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { handleReOrder() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Re-Order")
            }
        }
    }
}