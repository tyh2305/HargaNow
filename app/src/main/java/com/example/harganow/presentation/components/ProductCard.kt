package com.example.harganow.presentation.components


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.harganow.R
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.ui.theme.Orange
import com.example.harganow.utils.ImageGetter
import com.example.harganow.utils.NavInfo
import com.example.harganow.utils.SSLUnsafeImage



@Composable
fun ProductCard(
//    _state: Boolean,
    productId: String,
    productName: String,
    productUnit: String,
    price: Double,
    width: Dp,
    navigateToProductDetail: () -> Unit
) {
    val tag = "ProductCard:$productId"

    fun retrieveImage(): String {
        val imageUrl = ImageGetter.GetImage(productId)
        Log.v(tag,"ImageUrl : $imageUrl")
        return imageUrl
    }

    fun priceToString(price: Double): String {
        // Return price in format RM 0.00
        return "RM ${String.format("%.2f", price)}"
    }

    Card(
        modifier = Modifier
            .clickable {
                NavInfo.itemId = productId
                navigateToProductDetail()
                Log.v(tag,"ProductCard $productName clicked")
            },
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Orange),
    ) {
        Column(
            modifier = Modifier
                .height(200.dp)
                .width(width)
                .padding(8.dp),
        ){
            Box(
                Modifier
                    .size(123.dp)
                    .clip(RoundedCornerShape(5.dp))
//                    .border(1.dp, Orange, RoundedCornerShape(5.dp))
                    .align(Alignment.CenterHorizontally)
            ) {
                SSLUnsafeImage(
                    context = LocalContext.current,
                    url = retrieveImage(),
                    contentDescription = productName,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                )
            }
            Text(text = priceToString(price), fontWeight = FontWeight.Bold)
            Text(
                text = "$productName $productUnit",
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
    }
}

//@Preview
//@Composable
//fun ProductCardBuildersPreview(){
//    val itemList: List<Item> = listOf(
//        Item(
//            id = "1",
//            item_category = "Fresh Goods",
//            item_group = "Meats",
//            item = "Whole Chicken",
//            unit = "IDK"
//        ),
//        Item(
//            id = "2",
//            item_category = "Fresh Goods",
//            item_group = "Meats",
//            item = "Chicken Breast",
//            unit = "IDK"
//        ),
//        Item(
//            id = "3",
//            item_category = "Fresh Goods",
//            item_group = "Meats",
//            item = "Chicken Thigh",
//            unit = "IDK"
//        ),
//    )
//
//    var premise: Premise = Premise(
//        id = "1",
//        premise = "Kedai Ayamas",
//        premise_type = "Restaurant",
//        state = "Selangor",
//        district = "Petaling",
//    )
//
//    val date: java.util.Date = java.util.Date()
//
//    var itemListWithPrice: List<ItemPrice> = listOf(
//        ItemPrice(
//            item = itemList[0],
//            premise = premise,
//            price = 19.90,
//            date = ItemDate.fromDate(date)
//        ),
//        ItemPrice(
//            item = itemList[1],
//            premise = premise,
//            price = 19.90,
//            date = ItemDate.fromDate(date)
//        ),
//        ItemPrice(
//            item = itemList[2],
//            premise = premise,
//            price = 19.90,
//            date = ItemDate.fromDate(date)
//        )
//    )
//
//    val itemPriceList: List<Double> = listOf(
//        19.90,
//        19.90,
//        19.90,
//    )
//
//    Column{
////        ProductCardLazyRowBuilder(itemListWithPrice = itemListWithPrice, navigateToProductDetail = {})
////        ProductCardRowBuilder(firstItem = itemList[0], secondItem = itemList[1], firstItemPrice = itemPriceList[0], secondItemPrice = itemPriceList[1])
//        ProductCardGridBuilder(itemWithPriceList = itemListWithPrice, navigateToProductDetail = {})
//
//    }
//
//}

@Composable
fun ProductCardLazyRowBuilder(
    itemWithPriceList: List<ItemPrice>,
    navigateToProductDetail: () -> Unit
) {
    val state = rememberLazyListState()
    val tag = "ProductCardLazyRowBuilder"

    LazyRow(
        state = state,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(itemWithPriceList.size) {
            Log.v(tag, "Item : ${itemWithPriceList[it].item.id}")
            ProductCard(
                productId = itemWithPriceList[it].item.id!!,
                productName = itemWithPriceList[it].item.item!!,
                productUnit = itemWithPriceList[it].item.unit!!,
                price = itemWithPriceList[it].price,
                width = 150.dp,
                navigateToProductDetail
            )
        }
    }
}



@Composable
fun ProductCardRowBuilder(
    firstItemWithPrice: ItemPrice?,
    secondItemWithPrice: ItemPrice?,
    navigateToProductDetail: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (firstItemWithPrice != null) {
            ProductCard(
                productId = firstItemWithPrice.item.id!!,
                productName = firstItemWithPrice.item.item!!,
                productUnit = firstItemWithPrice.item.unit!!,
                price = firstItemWithPrice.price,
                width = screenWidth/2 - 16.dp,
                navigateToProductDetail
            )
        }

        if(secondItemWithPrice != null){
            ProductCard(
                productId = secondItemWithPrice.item.id!!,
                productName = secondItemWithPrice.item.item!!,
                productUnit = secondItemWithPrice.item.unit!!,
                price = secondItemWithPrice.price,
                width = screenWidth/2 - 16.dp,
                navigateToProductDetail
            )
        }

        if(firstItemWithPrice == null && secondItemWithPrice == null){
            Text(
                text = stringResource(id = R.string.no_product_found),
            )
        }
    }
}

//@Composable
//fun ProductCardGridBuilder(
//    itemWithPriceList: List<ItemPrice>,
//    navigateToProductDetail: () -> Unit
//) {
//    val tag = "ProductCardGridBuilder"
//    val configuration = LocalConfiguration.current
//    val screenWidth = configuration.screenWidthDp.dp
//    val state = rememberLazyGridState()
//
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(2),
//        modifier = Modifier
//            .verticalScroll(rememberScrollState()),
//        state = state,
//        horizontalArrangement = Arrangement.spacedBy(8.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp),
//        contentPadding = PaddingValues(8.dp)
//    ) {
//        items(itemWithPriceList.size) {
//            Log.v(tag, "ProductCardGridBuilder: ${itemWithPriceList[it].item.item}")
//            ProductCard(
//                productId = itemWithPriceList[it].item.id!!,
//                productName = itemWithPriceList[it].item.item!!,
//                productUnit = itemWithPriceList[it].item.unit!!,
//                price = itemWithPriceList[it].price!!,
//                width = screenWidth/2 - 16.dp,
//                navigateToProductDetail
//            )
//        }
//    }
//}
