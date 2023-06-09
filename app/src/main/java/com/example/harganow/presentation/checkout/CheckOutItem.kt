package com.example.harganow.presentation.checkout

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.harganow.domain.model.Address
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.presentation.components.Header
import com.example.harganow.ui.theme.Orange
import com.example.harganow.utils.ImageGetter
import com.example.harganow.utils.SSLUnsafeImage
import kotlinx.coroutines.launch

fun Double.format(digits: Int) = "%.${digits}f".format(this)

@Composable
fun CheckOutItem(
    item: ItemPrice,
    count: Int
) {
    val TAG = "CheckOutItem"
    val url = ImageGetter.GetImage(item.item.id.toString())
    val productName = item.item.item.toString()
    Box(
        Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .shadow(
                elevation = 0.5.dp,
                shape = RoundedCornerShape(5.dp),
                clip = true
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(20.dp)
        ) {
            Box(
                Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(5.dp))
            ) {
                SSLUnsafeImage(
                    context = LocalContext.current,
                    url = url,
                    contentDescription = productName
                )
            }
            Column(
                Modifier
                    .wrapContentSize()
                    .padding(start = 20.dp)
            ) {
                Text(
                    text = productName,
                )
                Text(
                    text = "RM ${item.price.format(2)}"
                )
                Text(
                    text = "x " + count.toString()
                )
            }
        }
    }
}

@Composable
fun CheckOutItemListBuilder(
    itemList: MutableList<Map<ItemPrice, Int>>,
    modifier: Modifier = Modifier
) {
    @Composable
    fun itemToCheckOutItem(ip: ItemPrice, count: Int) {
        CheckOutItem(ip, count)
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val columnHeight = (screenHeight * 0.3).dp

    Box(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.height(columnHeight),
            userScrollEnabled = true,
        ) {
            items(itemList.size) { index ->
                itemToCheckOutItem(
                    ip = itemList[index].keys.first(),
                    count = itemList[index].values.first()
                )
            }
        }
    }
}

@Preview
@Composable
fun CheckOutItemPreview() {
    val itemList: MutableList<Map<ItemPrice, Int>> = remember { mutableListOf() }
    val item = ItemPrice()
    item.item.id = 1.toString()
    item.item.item = "Nasi Lemak"
    item.price = 5.0
    val count = 1

    itemList.add(mapOf(item to count))
    val item2 = ItemPrice()
    item2.item.id = 2.toString()
    item2.item.item = "Chicken"
    item2.price = 10.0
    itemList.add(mapOf(item2 to count))

    val item3 = ItemPrice()
    item3.item.id = 3.toString()
    item3.item.item = "Chicken"
    item3.price = 10.5

    itemList.add(mapOf(item3 to count))

    val item4 = ItemPrice()
    item4.item.id = 4.toString()
    item4.item.item = "Chicken"
    item4.price = 10.5

    CheckOutItemListBuilder(itemList = itemList)
}

@Composable
fun ShippingOption(
    address: MutableState<Address>,
    navigateToAddress: () -> Unit,
    modifier: Modifier = Modifier
) {
    var defaultShowValue = "Enter Address"
    if (address.value.unitNumber != "" && address.value.unitNumber != null) {
        defaultShowValue = "Address Filled"
    }

    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Shipping Option",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp),
                color = Orange
            )
            Button(
                onClick = { navigateToAddress() }, modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(text = defaultShowValue)
            }
        }
    }
}

@Composable
fun PaymentOptionDialog(
    deliveryOption: MutableState<String>,
    openDialogUpdate: () -> Unit,
) {
    Dialog(
        onDismissRequest = {}
    ) {
        Box(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(10.dp))
                .width(600.dp)
        ) {
            Column {
                Text(
                    text = "Payment Option",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        deliveryOption.value = "Cash On Delivery"
                        openDialogUpdate()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(text = "Cash On Delivery")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        deliveryOption.value = "Online Payment"
                        openDialogUpdate()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(text = "Online Payment")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        deliveryOption.value = "Bank Transfer"
                        openDialogUpdate()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(text = "Bank Transfer")
                }
            }
        }
    }
}

@Composable
fun PaymentOption(deliveryOption: MutableState<String>, modifier: Modifier = Modifier) {
    var defaultTextValue = "Select Payment Option"
    var displayTextValue =
        if (deliveryOption.value == "") defaultTextValue else deliveryOption.value
    var openDialog by remember { mutableStateOf(false) }
    val openDialogUpdate = { openDialog = false }

    if (openDialog) {
        PaymentOptionDialog(deliveryOption, openDialogUpdate)
    }

    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Payment Option",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp),
                color = Orange
            )
            Button(
                onClick = { openDialog = true }, modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(text = displayTextValue)
            }
        }
    }
}

@Composable
fun PlaceOrderButton(
    modifier: Modifier = Modifier,
    onClick: suspend () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(
        modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = buildAnnotatedString {
                    append("By placing an order, you agree to our")
                    withStyle(style = SpanStyle(color = Orange, fontWeight = FontWeight.Bold)) {
                        append(" Terms And Conditions")
                    }
                })
            Button(
                onClick = {
                    scope.launch {
                        try {
                            onClick()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error : ${e.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Orange,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Place Order")
            }
        }
    }
}

@Composable
fun TotalPrice(
    modifier: Modifier = Modifier,
    totalPrice: Double
) {
    Box(
        modifier
            .fillMaxWidth()
            .padding(20.dp)
//            .clip(RoundedCornerShape(10.dp))
            .drawBehind {
                val strokeWidth = 3 * density
                val y = size.height - strokeWidth / 2
                drawLine(
                    Color.LightGray,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth
                )
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Price",
                textAlign = TextAlign.Center,
                style = androidx.compose.ui.text.TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
            Text(
                text = "RM ${totalPrice.format(2)}",
                textAlign = TextAlign.Center,
                style = androidx.compose.ui.text.TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        }
    }
}

@Composable
fun CheckOutBuilder(
    itemList: MutableList<Map<ItemPrice, Int>>,
    address: MutableState<Address>,
    paymentOption: MutableState<String>,
    handleCheckout: suspend () -> Unit,
    navigateToAddress: () -> Unit,
    navigateToPreviousStack: () -> Unit
) {
    var totalPrice = 0.0

    itemList.forEach {
        totalPrice += it.keys.first().price * it.values.first()
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header("Check Out", 10, navigateToPreviousStack)
        CheckOutItemListBuilder(itemList = itemList, modifier = Modifier.weight(1f, true))
        ShippingOption(address, navigateToAddress)
        PaymentOption(paymentOption)
        TotalPrice(totalPrice = totalPrice)
        PlaceOrderButton(onClick = handleCheckout)
    }
}