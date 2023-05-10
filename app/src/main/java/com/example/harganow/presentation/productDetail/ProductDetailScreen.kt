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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.harganow.ui.theme.Orange
import com.example.harganow.utils.NavInfo

@ExperimentalCoroutinesApi
@Composable
fun CircularProgressBar(isDisplayed: Boolean) {
    if(isDisplayed) {
        CircularProgressIndicator()
    }
}

@Composable
fun ProductDetailScreen(
    navigateToPreviousStack: () -> Unit,
    navigateToProductDetail: () -> Unit,
) {
    val itemId = NavInfo.itemId

    val tag = "ProductDetailScreen"
    val productDetailViewModel = ProductDetailViewModel(itemId = itemId)

    var quantity by remember { mutableStateOf(1) }

    fun retrieveImage(): String {
        val imageUrl = ImageGetter.GetImage(itemId)
        Log.v(tag,"ImageUrl : $imageUrl")
        return imageUrl
    }

    Column {
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
                SSLUnsafeImage(
                    context = LocalContext.current,
                    url = retrieveImage(),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .fillMaxSize()
                )
                Button(
                    onClick = {
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

            Spacer(modifier = Modifier
                .padding(16.dp)
                .background(Color.LightGray)
            )

            Box(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Column() {
                    Row() {
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
                ){
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
