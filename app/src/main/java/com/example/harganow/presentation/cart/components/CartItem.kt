package com.example.harganow.presentation.cart.components

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.harganow.domain.model.Item
import com.example.harganow.domain.model.ItemDate
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.domain.model.Premise
import com.example.harganow.utils.ImageGetter
import com.example.harganow.utils.SSLUnsafeImage
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Composable
fun CartItem(
    _state: Boolean, productId: String, productName: String, price: Double, onClick: (() -> Unit)?
) {
    val TAG = "CartItem:$productId"
    var state: Boolean = _state
    var productImage: Uri
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
        state = !state
        onClick?.invoke()
    }

    fun test(): String {
        return "https://myharga.kpdn.gov.my/myharga/mobile/apps/1.png"
    }

    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    })

    val asyncPainter = rememberAsyncImagePainter(test())

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
                .width(1000.dp)
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
                    checked = state,
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
            }

        }
    }
}

@Composable
fun CartItemListBuilder(itemPriceList: List<ItemPrice>) {
    @Composable
    fun itemToCartItem(ip: ItemPrice): Unit {
        return CartItem(
            _state = false,
            productId = ip.item.id,
            productName = ip.item.name,
            price = ip.price,
            onClick = null
        )
    }

    LazyColumn(
    ) {
        items(itemPriceList.size) { index ->
            itemToCartItem(itemPriceList[index])
        }
    }
}

@Preview
@Composable
fun CartItemPreview() {
    var itemList: List<Item> = listOf(
        Item(
            id = "1",
            category = "Fresh Goods",
            group = "Meats",
            name = "Whole Chicken",
            unit = "IDK"
        ),
        Item(
            id = "2",
            category = "Fresh Goods",
            group = "Meats",
            name = "Chicken Breast",
            unit = "IDK"
        ),
        Item(
            id = "3",
            category = "Fresh Goods",
            group = "Meats",
            name = "Chicken Thigh",
            unit = "IDK"
        ),
    )

    val date: java.util.Date = java.util.Date()
    val premise: Premise = Premise(
        id = "1",
        name = "Tesco",
        type = Premise.Companion.Place.PASAR_RAYA,
        district = "Kuala Lumpur",
        state = Premise.Companion.State.KUALA_LUMPUR,
    )
    var itemPriceList: List<ItemPrice> = listOf(
        ItemPrice(
            item = itemList[0], date = ItemDate.fromDate(date), premise = premise, price = 19.90
        ),
        ItemPrice(
            item = itemList[1], date = ItemDate.fromDate(date), premise = premise, price = 19.90
        ),
        ItemPrice(
            item = itemList[2], date = ItemDate.fromDate(date), premise = premise, price = 19.90
        ),
    )
    Surface(
        color = MaterialTheme.colors.background,
    ) {
        CartItemListBuilder(itemPriceList)
    }


//    CartItem(
//        _state = true,
//        productId = "1",
//        productName = "Whole Chicken",
//        price = 19.90,
//        onClick = null
//    )
}