package com.example.harganow.presentation.order

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.harganow.domain.model.Order
import com.example.harganow.presentation.components.Header

@Composable
fun OrderItemListBuilder(
    orderList: List<Order>,
    modifier: Modifier = Modifier,
) {
    @Composable
    fun orderToOrderItem(order: Order) {
        Text(text = order.toString())
    }

    Box(
        modifier = modifier
    ) {
        LazyColumn {
            items(orderList.size) { index ->
                orderToOrderItem(orderList[index])
            }
        }
    }
}

@Composable
fun OrderScreen(
    navigateToPreviousStack: () -> Unit,
    navigateToCheckOut: () -> Unit,
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
        Column(
            verticalArrangement = (if (orderViewModel.loading.value) Arrangement.Center else Arrangement.SpaceBetween),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Header(
                title = "Order",
                titleSize = 10,
                navigateToPreviousStack = navigateToPreviousStack
            )
            CircularProgressBar(
                isDisplayed = orderViewModel.loading.value
            )
            if (!orderViewModel.loading.value) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    OrderItemListBuilder(
                        orderList = orderViewModel.data,
                        modifier = Modifier.weight(1f, true),
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
        }
    }
}