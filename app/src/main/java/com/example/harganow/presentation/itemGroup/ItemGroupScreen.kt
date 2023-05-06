package com.example.harganow.presentation.itemGroup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harganow.presentation.components.BackButton
import com.example.harganow.utils.InitPriceRepo
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.harganow.presentation.components.ProductCardRowBuilder
import com.example.harganow.presentation.main.CircularProgressBar


//@Preview(showBackground = true, widthDp = 360, heightDp = 640)
//@Composable
//fun itemGroupScreenPreview(
//    initPriceRepo: InitPriceRepo = InitPriceRepo()
//) {
//    if(!initPriceRepo.loading.value) {
//        ItemGroupScreen(
//            navigateToPreviousStack = {},
//            navigateToProductDetail = {},
//        )
//    }
//}

@Composable
fun ItemGroupScreen(
    navigateToPreviousStack: () -> Unit,
    navigateToProductDetail: () -> Unit,
    itemGroupViewModel:ItemGroupViewModel = ItemGroupViewModel()
) {
//    val itemGroupViewModel = ItemGroupViewModel(itemGroup = itemGroup)

    var selectedCategory by remember {
        mutableStateOf("")
    }

    val updateCategory = { category: String ->
        selectedCategory = category
    }

    CircularProgressBar(isDisplayed = itemGroupViewModel.loading.value)

    if(!itemGroupViewModel.loading.value) {
        Box(Modifier.verticalScroll(rememberScrollState())) {
            Column() {

                // TODO: Change title, etc
                ItemGroupScreenHeader(
                    title = itemGroupViewModel.itemGroup,
                    navigateToPreviousStack = navigateToPreviousStack,
                    itemGroupViewModel = itemGroupViewModel,
                    updateCategory = updateCategory
                )
                Column() {
                    Spacer(modifier = Modifier.padding(24.dp))

                    // TODO: fix image bug
                    var filteredItemWithPriceList = if (selectedCategory == "") {
                        itemGroupViewModel.itemWithPriceList
                    } else {
                        itemGroupViewModel.itemWithPriceList.filter { item ->
                            item.item.item_category == selectedCategory
                        }
                    }

                    for(i in 0..filteredItemWithPriceList.size step 2) {
                        var firstItemWithPrice = filteredItemWithPriceList[i]
                        var secondItemWithPrice = if(i+1 < filteredItemWithPriceList.size) {
                            filteredItemWithPriceList[i+1]
                        } else {
                            null
                        }

                        ProductCardRowBuilder(
                            firstItemWithPrice = firstItemWithPrice,
                            secondItemWithPrice = secondItemWithPrice,
                            navigateToProductDetail = navigateToProductDetail
                        )
                    }

                }
            }

        }
    }
}

@Composable
fun ItemGroupScreenHeader(
    title: String,
    navigateToPreviousStack: () -> Unit,
    itemGroupViewModel: ItemGroupViewModel,
    updateCategory: (String) -> Unit
) {
    val itemCategoryList = itemGroupViewModel.itemCategoryList

    val state = rememberLazyListState()

    Surface(
        elevation = 10.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.White)
        ){
            Column() {
                Row() {
                    BackButton(navigateToPreviousStack = navigateToPreviousStack)
                    Text(
                        text = title,
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .align(Alignment.CenterVertically)
                    )
                    // TODO: navigate to search
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(40.dp)
                            .padding(start = 15.dp),
                        tint = Color.Black,
                    )
                    // TODO: navigate to cart
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(40.dp)
                            .padding(start = 15.dp),
                        tint = Color.Black,
                    )
                }
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(start = 10.dp, end = 10.dp),
                    state = state,
                ) {
                    items(itemCategoryList) {
                        // TODO: change color when selected
                        Box(
                            modifier = Modifier
                                .clickable {
                                    updateCategory(it)
                                }
                                .background(Color.White, RoundedCornerShape(30.dp))
                                .border(1.dp, Color.Gray, RoundedCornerShape(30.dp))

                        ){
                            Text(
                                text = it,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }

                }

            }

        }
    }
}

