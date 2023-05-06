package com.example.harganow.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.harganow.data.repository.PriceRepository
import com.example.harganow.presentation.components.BackButton
import com.example.harganow.presentation.components.ProductCardRowBuilder
import com.example.harganow.utils.InitPriceRepo

@Preview
@Composable
fun SearchScreenPreview(
    initPriceRepo: InitPriceRepo = InitPriceRepo()
){
    if(!initPriceRepo.loading.value) {
        SearchScreen(navigateToPreviousStack = {}, navigateToProductDetail = {})
    }
}

@Composable
fun SearchScreen(
    navigateToPreviousStack: () -> Unit,
    navigateToProductDetail: () -> Unit,
) {
    val itemWithPriceList = PriceRepository.itemWithLatestPriceList

    var searchQuery by remember {
        mutableStateOf("")
    }

    val updateSearchQuery = {
        newQuery: String ->
        searchQuery = newQuery
    }

    Box(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ){
        Column(){
            SearchScreenHeader(
                navigateToPreviousStack = navigateToPreviousStack,
                updateSearchQuery = updateSearchQuery,
            )
            Spacer(modifier = Modifier.padding(24.dp))
            //TODO: Image UI bug

            var filteredItemWithPriceList = if (searchQuery == "") {
                PriceRepository.itemWithLatestPriceList
            } else {
                PriceRepository.itemWithLatestPriceList.filter { it ->
                    it.item.item!!.contains(searchQuery, ignoreCase = true)
                }
            }

            if (filteredItemWithPriceList.isNotEmpty()){
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
            } else {
                ProductCardRowBuilder(
                    firstItemWithPrice = null,
                    secondItemWithPrice = null,
                    navigateToProductDetail = navigateToProductDetail
                )
            }



        }
    }
}

@Composable
fun SearchScreenHeader(
    navigateToPreviousStack: () -> Unit,
    updateSearchQuery: (String) -> Unit
) {
    var searchQuery by remember {
        mutableStateOf("")
    }

    Surface(
        elevation = 10.dp
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.White)
        ){
            Row(){
                BackButton(navigateToPreviousStack = navigateToPreviousStack)
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {searchQuery = it},
                    keyboardOptions = KeyboardOptions(
                        imeAction =  ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            updateSearchQuery(searchQuery)
                        }
                    ),
                    singleLine = true,
                )
                Box(
                    // TODO: navigate to cart
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable { }
                ){
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(start = 15.dp),
                        tint = Color.Black,
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun test() {
    if ("HALIA BASAH (TUA)".contains("test", ignoreCase = true)){
        Text(text = "true")
    }
}