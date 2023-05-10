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
import androidx.compose.ui.unit.dp
import com.example.harganow.data.repository.PriceRepository
import com.example.harganow.presentation.components.BackButton
import com.example.harganow.presentation.components.ProductCardMultipleRowBuilder


@Composable
fun SearchScreen(
    navigateToPreviousStack: () -> Unit,
    navigateToProductDetail: () -> Unit,
    navigateToCart: () -> Unit,
) {

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
                navigateToCart
            )
            Spacer(modifier = Modifier.padding(24.dp))
            //TODO: Image UI bug

            val filteredItemWithPriceList = if (searchQuery == "") {
                PriceRepository.itemWithLatestPriceList
            } else {
                PriceRepository.itemWithLatestPriceList.filter { it ->
                    it.item.item!!.contains(searchQuery, ignoreCase = true)
                }
            }

            ProductCardMultipleRowBuilder(filteredItemWithPriceList, navigateToProductDetail)

        }
    }
}

@Composable
fun SearchScreenHeader(
    navigateToPreviousStack: () -> Unit,
    updateSearchQuery: (String) -> Unit,
    navigateToCart: () -> Unit
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
                .height(100.dp)
                .background(Color.White)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ){
                BackButton(navigateToPreviousStack = navigateToPreviousStack)
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {searchQuery = it},
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 8.dp),
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
                // TODO: make shopping cart size bigger
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable {
                            navigateToCart()
                        }
                        .padding(end = 8.dp)
                        .align(Alignment.CenterVertically),
                ){
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "",
                        modifier = Modifier
                            .size(50.dp),
                        tint = Color.Black,
                    )
                }
            }
        }
    }
}

