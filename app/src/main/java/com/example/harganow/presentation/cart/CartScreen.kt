package com.example.harganow.presentation.cart

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.presentation.cart.components.CartItemListBuilder
import com.example.harganow.presentation.components.Header
import com.example.harganow.ui.theme.Orange
//import com.example.harganow.presentation.cart.components.CartItemListBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Composable
fun CircularProgressBar(isDisplayed: Boolean) {
    if (isDisplayed) {
        CircularProgressIndicator()
    }
}

@Composable
fun CartScreen(
    navigateToPreviousStack: () -> Unit,
    navigateToCheckout: () -> Unit,
    chosenList: MutableList<Map<ItemPrice, Int>>
) {
    val TAG = "CartScreen"
    val context = LocalContext.current
    val cartViewModel: CartViewModel = CartViewModel(chosenList)

    //    var chosenList: MutableList<Map<ItemPrice, Int>> = remember { mutableListOf() }
    fun handleCheckout() {
        chosenList.forEach { item ->
            Log.d("Chosen Item List", item.toString())
        }
        navigateToCheckout()
    }

    Surface(
        color = MaterialTheme.colors.background
    ) {
        Column(
            verticalArrangement = (if (cartViewModel.loading.value) Arrangement.Center else Arrangement.SpaceBetween),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Header(
                title = "Cart",
                titleSize = 10,
                navigateToPreviousStack = navigateToPreviousStack
            )
            CircularProgressBar(
                isDisplayed = cartViewModel.loading.value
            )
            if (!cartViewModel.loading.value) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    CartItemListBuilder(
                        modifier = Modifier.weight(1f, true),
                        itemList = cartViewModel.data,
                        selectedItem = chosenList
                    )
                    Box(
                        contentAlignment = Alignment.BottomCenter,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Button(
                            onClick = { handleCheckout() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 0.dp, horizontal = 20.dp)
                        ) {
                            Text(text = "Checkout")
                        }
                    }
                }
            }
        }
    }
}
