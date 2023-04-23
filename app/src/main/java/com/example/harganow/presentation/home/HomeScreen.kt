package com.example.harganow.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harganow.R
import com.example.harganow.presentation.components.ProductCard
import com.example.harganow.ui.theme.Orange


@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun HomeScreen(
    //navigateToPreviousStack: () -> Unit
) {
    Box(Modifier.verticalScroll(rememberScrollState())) {
        Column() {

            //Header(title = "Reset Password", titleSize = 64, navigateToPreviousStack = navigateToPreviousStack)
            HomeScreenHeader()
            Column() {
                ContentList()
            }
        }

    }
}

@Composable
fun HomeScreenHeader() {
    val premise = "AEON BIG (DANAU KOTA)"
    val location = "LOT PT 9830, JALAN LANGKAWI"
    var text = "Search"

    Surface(
        elevation = 20.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(145.dp)
                .background(Orange),
        ) {
            Column {
                Row(

                ){
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(start = 30.dp, top = 25.dp, bottom = 16.dp, end = 16.dp)
                            .size(24.dp)
                    )
                    Column(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .width(225.dp)
                    ) {
                        Text(text = premise, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, overflow = TextOverflow.Ellipsis, maxLines = 1)
                        Text(text = location, color = Color.White, modifier = Modifier.padding(top = 5.dp), fontSize = 12.sp, overflow = TextOverflow.Ellipsis, maxLines = 1)
                    }
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(top = 25.dp, start = 15.dp, end = 8.dp)
                            .size(25.dp)
                    )
                }
                Row(
                    Modifier.padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 10.dp),
                ){
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp)
                        ,
                        shape = RoundedCornerShape(16.dp),
                        placeholder = {
                            Text(text = "Search", color = Color.Gray)
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            textColor = Color.Black,
                            cursorColor = Color.Black,
                            placeholderColor = Color.Gray
                        )
                    )
                }
            }
        }
    }
}



@Composable
fun ContentList() {
    Column {
        Spacer(modifier = Modifier.padding(10.dp))
        Row(
            Modifier.align(Alignment.CenterHorizontally)
        ){
            Categories()
        }
        Spacer(modifier = Modifier.padding(24.dp))
        ScrollableProductCategories(categoryName = "Discover")
        Spacer(modifier = Modifier.padding(24.dp))
        ScrollableProductCategories(categoryName = "Fresh from Farm")
        Spacer(modifier = Modifier.padding(24.dp))
        ScrollableProductCategories(categoryName = "Quick and Easy")
    }
}

@Composable
fun Categories(){
    Column( //Align content to the center
    )
    {
        Row(
            Modifier.align(Alignment.CenterHorizontally)
        ) {
            CategoryCircle(imagePainter = painterResource(id = R.drawable.placeholder), "Milk & Baby Products")
            CategoryCircle(imagePainter = painterResource(id = R.drawable.placeholder), "Milk & Baby Products")
            CategoryCircle(imagePainter = painterResource(id = R.drawable.placeholder), "Milk & Baby Products")
            CategoryCircle(imagePainter = painterResource(id = R.drawable.placeholder), "Milk & Baby Products")
        }
        Row(
            Modifier.align(Alignment.CenterHorizontally)
        )
        {
            CategoryCircle(imagePainter = painterResource(id = R.drawable.placeholder), "Milk & Baby Products")
            CategoryCircle(imagePainter = painterResource(id = R.drawable.placeholder), "Milk & Baby Products")
            CategoryCircle(imagePainter = painterResource(id = R.drawable.placeholder), "Milk & Baby Products")
            CategoryCircle(imagePainter = painterResource(id = R.drawable.placeholder), "Milk & Baby Products")
        }
    }
}


//@Composable
//fun CategoryPreview(){
//    CategoryCircle(imagePainter = painterResource(id = R.drawable.placeholder), "Milk & Baby Products")
//}

@Composable
fun CategoryCircle(
    imagePainter: Painter,
    name: String = "",
){
    Column(
        Modifier
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(75.dp)
                .background(Color.White, shape = CircleShape)
                .border(1.dp, Orange, shape = CircleShape)
        ) {
            Image(
                painter = imagePainter,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(45.dp)
                    .align(Alignment.Center)
            )
        }
        Text(
            text = name,
            color = Color.Black,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp, bottom = 5.dp)
                .width(55.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }

}

//@Preview(showBackground = true, widthDp = 360, heightDp = 640)
//@Composable
//fun ScrollableProductCategoriesPreview(){
//    ScrollableProductCategories("Milk & Baby Products")
//}

@Composable
fun ScrollableProductCategories(
    categoryName: String,
) {
    val list = listOf(1,2,3,4,5,6,7,8,9,10)
    val state = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row {
            Text(
                text = categoryName,
                color = Orange,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 10.dp)
                    .width(275.dp)
            )
            Text(
                text = "View All",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 10.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        LazyRow(
            //contentPadding = PaddingValues(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            state = state,
        ) {
            items(list.size) {
                ProductCard(imagePainter = painterResource(id = R.drawable.placeholder), "Long Product Name Classicasdasdaasdasdasdasd 100g", "RM 10.00")
            }
        }
    }
}

