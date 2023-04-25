package com.example.harganow.presentation.cart.components

import ItemRepository
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.harganow.data.repository.CartRepository
import com.example.harganow.domain.model.Cart
import com.example.harganow.domain.model.Item
import com.example.harganow.domain.model.ItemDate
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.domain.model.Premise
import com.example.harganow.utils.ImageGetter
import com.example.harganow.utils.SSLUnsafeImage
import kotlinx.coroutines.launch
import java.security.cert.X509Certificate
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Composable
fun CartItem(
    _state: Boolean,
    productId: String,
    productName: String,
    price: Double,
    count: Int,
    onClick: (() -> Unit)?
) {
    val TAG = "CartItem:$productId"
    var state = remember { mutableStateOf(_state) }
    fun retrieveImage(): String {
        var imageUrl = ImageGetter.GetImage(productId)
        Log.v(TAG, "ImageUrl : $imageUrl")
        return imageUrl
    }

    fun priceToString(price: Double): String {
        // Return price in format RM 0.00
        return "RM ${String.format("%.2f", price)}"
    }

    fun handleClick(): Unit {
        state.value = !state.value
        onClick?.invoke()
    }

    // UI
    Box(
        modifier = Modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp))
            .shadow(
                elevation = 0.5.dp,
                shape = RoundedCornerShape(5.dp),
                clip = true
            )
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .height(120.dp)
                .padding(20.dp, 20.dp, 20.dp, 20.dp),
            horizontalArrangement = Arrangement.Start,
        ) {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Checkbox(
                    checked = state.value,
                    onCheckedChange = { handleClick() },
                )
            }
            Box(
                Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(5.dp))
            ) {
                SSLUnsafeImage(
                    context = LocalContext.current,
                    url = retrieveImage(),
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
                    text = priceToString(price)
                )
                CartItemCount(count, null)
            }
        }
    }
}

@Composable
fun CartItemCount(_count: Int, removeFromList: (() -> Unit)?) {
    val count = remember { mutableStateOf(_count) }

    fun handleMinus() {
        if (count.value > 0) {
            count.value -= 1
        } else {
            if (removeFromList != null) {
                removeFromList()
            }
        }
    }

    fun handleAdd() {
        count.value += 1
    }

    Box(
        Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = { handleMinus() },
                Modifier.size(30.dp),
                content = { Icon(Icons.Filled.KeyboardArrowLeft, "add") }
            )
            Box(
                modifier = Modifier
                    .size(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
//                    modifier = Modifier.wrapContentHeight(),
//                    textAlign = TextAlign.Center,
                    text = count.value.toString(),
                )
            }
            IconButton(
                onClick = { handleAdd() },
                Modifier.size(30.dp),
                content = { Icon(Icons.Filled.KeyboardArrowRight, "add") }
            )
        }
    }
}

@Composable
fun CartItemListBuilder(itemList: MutableList<Map<ItemPrice, Int>>) {
    val coroutineScope = rememberCoroutineScope()

    @Composable
    fun itemToCartItem(ip: ItemPrice, count: Int): Unit {
        return CartItem(
            _state = false,
            productId = ip.item.id!!,
            productName = ip.item.item!!,
            price = ip.price,
            count = count,
            onClick = null
        )
    }

    LazyColumn(
    ) {
        items(itemList.size) { index ->
            itemToCartItem(itemList[index].keys.first(), itemList[index].values.first())
        }
    }
}

@Preview
@Composable
fun CartItemPreview() {
    MaterialTheme {
        Surface {
            CartItem(
                _state = false,
                productId = "1",
                productName = "Product Name",
                price = 1.0,
                count = 1,
                onClick = null
            )
        }
    }
}

@Preview
@Composable
fun CountPreview() {
    fun removeFromList() {
        Log.v("CountPreview", "Remove from list")
    }

    MaterialTheme {
        Surface {
            CartItemCount(_count = 1, removeFromList = { removeFromList() })
        }
    }
}

