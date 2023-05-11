package com.example.harganow.presentation.order

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.harganow.domain.model.CartItem
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.presentation.cart.CircularProgressBar
import com.example.harganow.ui.theme.Orange
import com.example.harganow.domain.model.Order
import com.example.harganow.presentation.components.Header
import com.example.harganow.utils.ImageGetter
import com.example.harganow.utils.SSLUnsafeImage
import java.text.DecimalFormat
import java.text.SimpleDateFormat

@Composable
fun OrderItemListBuilder(
    orderList: List<Order>,
    modifier: Modifier = Modifier,
    orderViewModel: OrderViewModel
) {
    @Composable
    fun orderToOrderItem(order: Order) {

        var firstItem: CartItem = order.items[0]
        var itemPrice: ItemPrice? = orderViewModel.getItemWithId(firstItem.id)
        var context = LocalContext.current
        var url = ImageGetter.GetImage(itemPrice?.item?.id.toString())
        var contentDes = itemPrice?.item?.item.toString()
        Box(
            modifier = Modifier.padding(8.dp)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Orange)
                    .clip(RoundedCornerShape(16.dp))
//                    .shadow(4.dp, RoundedCornerShape(8.dp), clip = true)
                    .padding(16.dp)
            ) {
                Column {
                    Text(text = itemPrice?.item?.item.toString(), color = Color.White)
                    Text(
                        text = "RM ${DecimalFormat("0.00").format(if (itemPrice?.price == null) 0 else itemPrice.price)}",
                        color = Color.White
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(text = "Total:", color = Color.White)
                    Text(
                        text = "RM ${DecimalFormat("0.00").format(order.amount)}",
                        color = Color.White
                    )
                }
                Text(
                    text = SimpleDateFormat("dd-MM-yyyy").format(order.timestamp),
                    color = Color.White
                )
            }
        }
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
                navigateToPreviousStack = navigateToPreviousStack,
                displayBack = false
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
                        orderViewModel = orderViewModel
                    )
                }
            }
        }
    }
}