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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harganow.presentation.components.BackButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.harganow.domain.model.convertToItemGroupNameId
import com.example.harganow.presentation.components.ProductCardMultipleRowBuilder
import com.example.harganow.presentation.main.CircularProgressBar
import com.example.harganow.ui.theme.Orange
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ItemGroupScreen(
    navigateToPreviousStack: () -> Unit,
    navigateToProductDetail: () -> Unit,
    navigateToCart: () -> Unit,
    navigateToSearch: () -> Unit,
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

                ItemGroupScreenHeader(
                    title = itemGroupViewModel.itemGroup,
                    navigateToPreviousStack = navigateToPreviousStack,
                    itemGroupViewModel = itemGroupViewModel,
                    updateCategory = updateCategory,
                    navigateToCart,
                    navigateToSearch
                )
                Column() {
                    Spacer(modifier = Modifier.padding(24.dp))

                    // TODO: fix image bug
                    val filteredItemWithPriceList = if (selectedCategory == "") {
                        itemGroupViewModel.itemWithPriceList
                    } else {
                        itemGroupViewModel.itemWithPriceList.filter { item ->
                            item.item.item_category == selectedCategory
                        }
                    }

                    ProductCardMultipleRowBuilder(
                        itemWithPriceList = filteredItemWithPriceList,
                        navigateToProductDetail = navigateToProductDetail
                    )

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
    updateCategory: (String) -> Unit,
    navigateToCart: () -> Unit,
    navigateToSearch: () -> Unit
) {
    val itemCategoryList = itemGroupViewModel.itemCategoryList

    val state = rememberLazyListState()

    val categoryNameId: Int = convertToItemGroupNameId(title)

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
                        text = stringResource(id = categoryNameId),
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 15.dp)
                            .clickable {
                                navigateToSearch()
                            }
                    ){
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "",
                            modifier = Modifier
                                .size(40.dp),
                            tint = Color.Black,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 15.dp)
                            .clickable {
                                navigateToCart()
                            }
                    ){
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "",
                            modifier = Modifier
                                .size(40.dp),
                            tint = Color.Black,
                        )
                    }
                }

                var selectedCategory by remember {
                    mutableStateOf("")
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(start = 10.dp, end = 10.dp),
                    state = state,
                ) {
                    items(itemCategoryList) {
                        val color = if (selectedCategory == it) Orange else Color.White
                        val textColor = if (selectedCategory == it) Color.White else Color.Black

                        Box(
                            modifier = Modifier
                                .clickable {
                                    if(selectedCategory == it) {
                                        selectedCategory = ""
                                        updateCategory("")
                                    } else {
                                        selectedCategory = it
                                        updateCategory(it)
                                    }
                                }
                                .background(color, RoundedCornerShape(30.dp))
                                .border(1.dp, Color.Gray, RoundedCornerShape(30.dp))

                        ){
                            Text(
                                text = it,
                                modifier = Modifier.padding(5.dp),
                                color = textColor
                            )
                        }
                    }

                }

            }

        }
    }
}

