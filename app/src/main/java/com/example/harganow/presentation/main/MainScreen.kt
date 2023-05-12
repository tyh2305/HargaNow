package com.example.harganow.presentation.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harganow.presentation.components.ProductCardLazyRowBuilder
import com.example.harganow.ui.theme.Orange
import com.example.harganow.utils.ImageGetter
import com.example.harganow.utils.SSLUnsafeImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.example.harganow.R
import com.example.harganow.data.repository.PriceRepository
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.domain.model.convertToItemGroupNameId
import com.example.harganow.utils.NavInfo


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun MainScreen(
    navigateToCart: () -> Unit,
    navigateToProductDetail: () -> Unit,
    navigateToItemGroupScreen: () -> Unit,
    navigateToSearch: () -> Unit,
) {
    val tag = "HomeScreen"

    var openDialog by rememberSaveable { mutableStateOf(true) }
    var loadingFin by rememberSaveable { mutableStateOf(false) }

    val openDialogUpdate = {
        openDialog = false
    }

    val loadingFinUpdate = { it:Boolean ->
        loadingFin = it
    }

    val mainViewModel = MainViewModel(loadingFinUpdate)

    if (openDialog) {
        LocationDialog(mainViewModel = mainViewModel, loadCancel = false, openDialogUpdate)
    }

    Box(Modifier.verticalScroll(rememberScrollState())) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ){
            Spacer(modifier = Modifier.weight(1f))
            CircularProgressBar(isDisplayed = !loadingFin)
            Spacer(modifier = Modifier.weight(1f))
        }

        if (loadingFin) {
            Column {
                if (!mainViewModel.loading.value) {
                    HomeScreenHeader(navigateToSearch, mainViewModel, navigateToCart)
                    ContentList(mainViewModel, navigateToProductDetail, navigateToItemGroupScreen)
                }
                Log.v(tag, "content loaded")
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun CircularProgressBar(isDisplayed: Boolean) {
    if(isDisplayed) {
        CircularProgressIndicator()
    }
}


@Composable
fun HomeScreenHeader(
    navigateToSearch: () -> Unit,
    mainViewModel: MainViewModel,
    navigateToCart: () -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }

    val openDialogUpdate = {
        openDialog = false
    }

    if(openDialog){
        LocationDialog(mainViewModel = mainViewModel, loadCancel = true, openDialogUpdate)
    }

    val premiseName: String = PriceRepository.itemWithLatestPriceList[0].premise.premise

    Surface(
        elevation = 20.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Orange),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(bottom = 10.dp)
                ){
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clickable {
                                openDialog = true
                            }
                    ){
                        Row{
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .size(24.dp)
                            )
                            Column(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                            ) {
                                Text(text = premiseName, color = Color.White, fontWeight = FontWeight.Bold, overflow = TextOverflow.Ellipsis, maxLines = 1)
                            }
                        }
                    }

                    Spacer(Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.CenterVertically)
                            .padding(top = 5.dp)
                            .clickable {
                                navigateToCart()
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(24.dp)
                        )
                    }

                }
                Row(
                    Modifier
                        .padding(top = 5.dp, bottom = 20.dp, start = 8.dp, end = 8.dp)
                        .height(48.dp),
                ){
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .background(Color.White, RoundedCornerShape(10.dp))
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                            .height(40.dp)
                            .clickable {
                                navigateToSearch()
                            }
                    ){
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_search_24),
                                contentDescription = "Search",
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                tint = Color.Black,
                            )
                            Text(
                                text = stringResource(id = R.string.search),
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 8.dp),
                                )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ContentList(
    mainViewModel: MainViewModel,
    navigateToProductDetail: () -> Unit,
    navigateToItemGroupScreen: () -> Unit,
) {
    Column {
        Spacer(modifier = Modifier.padding(5.dp))
        Row(
            Modifier.align(Alignment.CenterHorizontally)
        ){
            Categories(navigateToItemGroupScreen)
        }
        Spacer(modifier = Modifier.padding(5.dp))

        val itemGroupItemWithPriceMap = LinkedHashMap<String, List<ItemPrice>>()

        val itemGroupNamesRaw = stringArrayResource(id = R.array.itemGroupNameRaw)

        for (i in itemGroupNamesRaw.indices){
            itemGroupItemWithPriceMap[itemGroupNamesRaw[i]] = PriceRepository.itemWithLatestPriceList.filter { it.item.item_group == itemGroupNamesRaw[i] }
        }

        //Remove empty list from itemGroupItemWithPriceMap
        itemGroupItemWithPriceMap.entries.removeIf { it.value.isEmpty() }

        if(!mainViewModel.loading.value) {
            for(item in itemGroupItemWithPriceMap){
                ScrollableProductItemGroup(navigateToProductDetail, navigateToItemGroupScreen,item.value
                )
                Spacer(modifier = Modifier.padding(5.dp))
            }
        }
    }
}

@Composable
fun Categories(
    navigateToItemGroupScreen: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    )
    {
        Row{
            CategoryCircle(1458, "BARANGAN SEGAR", navigateToItemGroupScreen)
            CategoryCircle(129, "BARANGAN KERING", navigateToItemGroupScreen)
            CategoryCircle(272, "BARANGAN BERBUNGKUS", navigateToItemGroupScreen)
        }
        Row {
            CategoryCircle(226, "MINUMAN", navigateToItemGroupScreen)
            CategoryCircle(1970, "SUSU DAN BARANGAN BAYI", navigateToItemGroupScreen)
            CategoryCircle(1980, "PRODUK KEBERSIHAN", navigateToItemGroupScreen)
        }
    }
}

@Composable
fun CategoryCircle(
    productId: Int,
    name: String = "",
    navigateToItemGroupScreen: () -> Unit,
){
    val tag = "CategoryCircle"

    fun retrieveImage(): String {
        val imageUrl = ImageGetter.GetImage(productId.toString())
        Log.v(tag,"ImageUrl : $imageUrl")
        return imageUrl
    }

    Column(
        Modifier
            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
    ) {

        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.White, shape = CircleShape)
                .border(1.dp, Orange, shape = CircleShape)
                .clickable {
                    NavInfo.itemGroup = name
                    navigateToItemGroupScreen()
                }
        ) {
            SSLUnsafeImage(
                context = LocalContext.current,
                url = retrieveImage(),
                contentDescription = "",
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.Center)
            )
        }

        val categoryNameId: Int = convertToItemGroupNameId(name)

        Text(
            text = stringResource(id = categoryNameId),
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp, bottom = 5.dp)
                .width(80.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }

}



@Composable
fun ScrollableProductItemGroup(
    navigateToProductDetail: () -> Unit,
    navigateToItemGroupScreen: () -> Unit,
    itemWithPriceList: List<ItemPrice>
) {

    val itemGroupNameId:Int = convertToItemGroupNameId( itemWithPriceList[0].item.item_group!!)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row {
            Text(
                text = stringResource(id = itemGroupNameId),
                color = Orange,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                Modifier
                    .align(Alignment.CenterVertically)
                    .clickable {
                        NavInfo.itemGroup = itemWithPriceList[0].item.item_group!!
                        navigateToItemGroupScreen()
                    }
            ){
                Text(
                    text = stringResource(id = R.string.view_all),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(start = 10.dp, bottom = 10.dp)

                )
            }

        }

        ProductCardLazyRowBuilder(itemWithPriceList = itemWithPriceList, navigateToProductDetail = navigateToProductDetail)
    }
}

@Composable
fun LocationDialog(
    mainViewModel: MainViewModel,
    loadCancel: Boolean,
    openDialogUpdate: () -> Unit,
) {

    Dialog(
        onDismissRequest = {},
        content = {
            Box(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(10.dp))
            ){
                Column {
                    var selected by remember { mutableStateOf(false) }

                    val updateSelected = {
                        selected = true
                    }

                    StateExposedDropdownMenuBox()

                    DistrictExposedDropdownMenuBox()

                    PremiseExposedDropdownMenuBox(mainViewModel = mainViewModel, updateSelected)

                    Spacer(modifier = Modifier.padding(24.dp))

                    val context = LocalContext.current

                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ){
                        if(loadCancel){
                            Button(
                                onClick = {
                                    openDialogUpdate()
                                },
                                modifier = Modifier
                                    .padding(8.dp)
                            ) {
                                Text(text = stringResource(id = R.string.cancel))
                            }
                        }
                        Button(
                            onClick = {
                                if(selected){
                                    openDialogUpdate()
                                    mainViewModel.getPremiseData()
                                } else {
                                    Toast.makeText(context, "Please select a premise", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier
                                .padding(8.dp)
                        ) {
                            Text(text = stringResource(id = R.string.confirm))
                        }

                    }

                }
            }
        }
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StateExposedDropdownMenuBox(
){
    val context = LocalContext.current

    val locationArray:Array<String> = context.resources.getStringArray(R.array.stateNames)

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(locationArray[5]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                enabled = false,
                readOnly = true,
                label = {
                    Text(
                        text = stringResource(R.string.state_label),
                    )
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                modifier = Modifier
                    .fillMaxWidth()
//                    .height(56.dp),
//                maxLines = 1,
            )
            ExposedDropdownMenu(
                expanded = false,
                onDismissRequest = {}
            ) {
                locationArray.forEach { location ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = location
                            expanded = false
//                            stateSelected = location

                        }
                    ) {
                        Text(
                            text = location,
                            color = Color.Black,
                        )
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DistrictExposedDropdownMenuBox(
){
    val context = LocalContext.current

    val locationArray:Array<String> = context.resources.getStringArray(R.array.W_P_KualaLumpurDistrictNames)

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(locationArray[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                enabled = false,
                readOnly = true,
                label = {
                    Text(
                        text = stringResource(R.string.district_label),
                    )
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                modifier = Modifier
                    .fillMaxWidth()
//                    .height(56.dp),
//                maxLines = 1,
            )
            ExposedDropdownMenu(
                expanded = false,
                onDismissRequest = {}
            ) {
                locationArray.forEach { location ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = location
                            expanded = false
//                            stateSelected = location

                        }
                    ) {
                        Text(
                            text = location,
                            color = Color.Black,
                        )
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PremiseExposedDropdownMenuBox(
    mainViewModel: MainViewModel,
    updateSelected: () -> Unit
){
    val context = LocalContext.current

    val premiseArray:Array<String> = context.resources.getStringArray(R.array.WangsaMajuPremise)

    var expanded by remember { mutableStateOf(false) }

    val selectPremise = stringResource(R.string.selectPremise)

    var selectedText by remember { mutableStateOf(selectPremise) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                label = {
                            Text(
                                text = stringResource(R.string.premise_label),
                            )
                        },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false  }
            ) {
                premiseArray.forEach { premise ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = premise
                            expanded = false
                            mainViewModel.premiseName = premise
                            updateSelected()
                        }
                    ) {
                        Text(
                            text = premise,
                            color = Color.Black,
                        )
                    }
                }

            }
        }
    }
}

