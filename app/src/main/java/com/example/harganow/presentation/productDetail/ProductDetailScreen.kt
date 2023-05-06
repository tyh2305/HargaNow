@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.harganow.presentation.productDetail

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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

    Column(){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            BackButton {
                navigateToPreviousStack()
            }
            SSLUnsafeImage(
                context = LocalContext.current,
                url = retrieveImage(),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxSize()
            )
            Button( // TODO: Analytics button
                onClick = {
                    Log.v(tag,"Button Clicked")
                },
            ) {
                // TODO: Trends Icon
                Text(text = "Analytics")
            }
        }

        CircularProgressBar(isDisplayed = productDetailViewModel.loading.value)

        // Observe loading of view model, if loading finish then show the text
        if(!productDetailViewModel.loading.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
//                        .padding(16.dp)
            ) {
                Text(
                    text = productDetailViewModel.itemWithPrice.item.item!! + " " + productDetailViewModel.itemWithPrice.item.unit!!,
                )
                Text(text = productDetailViewModel.itemWithPrice.price.toString())

            }
            Text(text = productDetailViewModel.itemWithPrice.premise.premise)

            Box(){
                Column() {
                    Row(){
                        Text(text = stringResource(id = R.string.see_alternatives))
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = stringResource(id = R.string.view_all))
                    }
                    ProductCardLazyRowBuilder(itemWithPriceList = productDetailViewModel.alternativeItemWithPriceList, navigateToProductDetail = navigateToProductDetail)
                }
            }
            Row(){

                Button(
                    onClick = {
                        quantity--
                        Log.v(tag,"Button Clicked")
                    },
                    enabled = quantity > 1

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_remove_24),
                        contentDescription = "Decrease Quantity",
                        tint = MaterialTheme.colors.onSurface,
                    )
                }

                Text(
                    text = quantity.toString(),
                )

                Button(
                    onClick = {
                        quantity++
                        Log.v(tag,"Button Clicked")
                    }

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
                        Log.v(tag,"Button Clicked")
                    }

                ) {
                    Text(text = stringResource(id = R.string.add_to_cart))
                }
            }

        }
    }

}
