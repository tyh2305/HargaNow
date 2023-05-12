@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.harganow.presentation.productDetail

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.harganow.presentation.components.BackButton
import com.example.harganow.utils.ImageGetter
import com.example.harganow.utils.SSLUnsafeImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.compose.ui.res.stringResource
import com.example.harganow.R
import com.example.harganow.presentation.components.ProductCardLazyRowBuilder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.ui.theme.Orange
import com.example.harganow.utils.NavInfo
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf

@ExperimentalCoroutinesApi
@Composable
fun CircularProgressBar(isDisplayed: Boolean) {
    if (isDisplayed) {
        CircularProgressIndicator()
    }
}

@Composable
fun AnalyticsDialog(
    itemList: Array<ItemPrice>,
    openDialogUpdate: () -> Unit,
) {
    val prices = itemList.map { it.price }.reversed()
    val priceList = prices.mapIndexed { index, price ->
        FloatEntry(x = index.toFloat(), y = price.toFloat())
    }

    // Average of the prices
    val average = prices.average()
    // Format the average as a string with two decimal places
    val averageString = String.format("%.2f", average)
    val max = itemList.maxByOrNull { it.price }
    val min = itemList.minByOrNull { it.price }
    val chartEntryModel = entryModelOf(priceList)
    Dialog(
        onDismissRequest = {
        }

    ) {
        Box(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(10.dp))
                .width(600.dp)
        ) {
            Column {
                Text("Price Analytics",
                    modifier = Modifier
                    .padding(12.dp))
                Chart(
                    chart = lineChart(),
                    model = chartEntryModel,
                    startAxis = startAxis(),
                    bottomAxis = bottomAxis(),
                )
                Row {
                    Text("Average",
                        modifier = Modifier
                        .padding(12.dp))
                    Text(
                        text = averageString,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
                Row {
                    Text("Max",
                        modifier = Modifier
                            .padding(12.dp))
                    Text(
                        text = max?.price.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
                Row {
                    Text("Min",
                        modifier = Modifier
                            .padding(12.dp))
                    Text(
                        text = min?.price.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
                Button(
                    onClick = {
                        openDialogUpdate()
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .align(Alignment.End)
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        }
    }
}

@Composable
fun ProductDetailScreen(
    navigateToPreviousStack: () -> Unit,
    navigateToProductDetail: () -> Unit,
    navigateToCart: () -> Unit,
) {
    val itemId = NavInfo.itemId

    val tag = "ProductDetailScreen"
    val productDetailViewModel = ProductDetailViewModel(itemId = itemId)
    var quantity by remember { mutableStateOf(1) }
    var openDialog by rememberSaveable { mutableStateOf(false) }

    fun retrieveImage(): String {
        val imageUrl = ImageGetter.GetImage(itemId)
        Log.v(tag, "ImageUrl : $imageUrl")
        return imageUrl
    }

    val openDialogUpdate = {
        openDialog = false
    }

    if (openDialog) {
        AnalyticsDialog(
            itemList = productDetailViewModel.priceList,
            openDialogUpdate = openDialogUpdate
        )
    }

    Column(Modifier.verticalScroll(rememberScrollState())) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            ) {
                BackButton(
                    navigateToPreviousStack = navigateToPreviousStack,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopStart)
                )
                Button(
                    onClick = {
                        navigateToCart()
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopEnd),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Orange),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Cart",
                        modifier = Modifier
                            .fillMaxSize(0.5f),
                        tint = Color.White,
                    )
                }
                SSLUnsafeImage(
                    context = LocalContext.current,
                    url = retrieveImage(),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .fillMaxSize()
                )
                Button(
                    onClick = {
                        // TODO: Open Analytics
                        openDialog = true
                        Log.v(tag, "Button Clicked")
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.BottomEnd),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Orange),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_trending_up_24),
                        contentDescription = "Analytics",
                        modifier = Modifier
                            .fillMaxSize(0.5f),
                        tint = Color.White,
                    )
                }

            }

            if (!productDetailViewModel.loading.value) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 8.dp, end = 8.dp)
                ) {

                    Text(
                        text = productDetailViewModel.itemWithPrice.item.item!! + " " + productDetailViewModel.itemWithPrice.item.unit!!,
                        modifier = Modifier
                            .widthIn(max = 280.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    val price = productDetailViewModel.itemWithPrice.price

                    Text(
                        text = "RM ${String.format("%.2f", price)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    )

                }
            }
        }

        CircularProgressBar(isDisplayed = productDetailViewModel.loading.value)

        // Observe loading of view model, if loading finish then show the text
        if (!productDetailViewModel.loading.value) {

            Spacer(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.LightGray)
            )

            Box(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Column {
                    Row {
                        Text(
                            text = stringResource(id = R.string.see_alternatives),
                            color = Orange,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    ProductCardLazyRowBuilder(
                        itemWithPriceList = productDetailViewModel.alternativeItemWithPriceList,
                        navigateToProductDetail = navigateToProductDetail
                    )
                }
            }

            Box(
                modifier = Modifier
                    .height(16.dp)
                    .background(color = Color.LightGray)
            )

            Row(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
            ) {

                OutlinedButton(
                    onClick = {
                        quantity--
                        Log.v(tag, "Button Clicked")
                    },
                    enabled = quantity > 1,
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(0)

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_remove_24),
                        contentDescription = "Decrease Quantity",
                        tint = MaterialTheme.colors.onSurface,
                    )
                }

                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                        )
                        .padding(start = 25.dp, end = 25.dp, top = 5.dp)
                        .align(Alignment.CenterVertically),
                ) {
                    Text(
                        text = quantity.toString(),
                        fontSize = 20.sp,
                    )
                }

                OutlinedButton(
                    onClick = {
                        quantity++
                        Log.v(tag, "Button Clicked")
                    },
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(0)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_24),
                        contentDescription = "Increase Quantity",
                        tint = MaterialTheme.colors.onSurface,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        productDetailViewModel.handleAddToCart(quantity)
                        Log.v(tag, "Button Clicked")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Orange),
                ) {
                    Text(
                        text = stringResource(id = R.string.add_to_cart),
                        color = Color.White,
                    )
                }
            }

        }
    }
}
