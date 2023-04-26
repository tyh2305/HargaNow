package com.example.harganow.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harganow.R
import com.example.harganow.ui.theme.Orange

@Preview (showBackground = false)
@Composable
fun ProductCardPreview(){
    ProductCard(imagePainter = painterResource(id = R.drawable.placeholder), title = "Long Product Name Classicasdasdaasdasdasdasd 100g", price = "RM 10.00")
}

@Composable
fun ProductCard(
    imagePainter: Painter,
    title: String = "",
    price: String = ""
) {
    Card(
        modifier = Modifier
            .width(123.dp)
            .clickable {
            },
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Orange),
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(123.dp)
                    .width(123.dp)
                    .padding(8.dp)
            ) {
                Image(
                    contentScale = ContentScale.Fit,
                    painter = imagePainter,
                    contentDescription = "",
                    modifier = Modifier.aspectRatio(1f)
                )
                Surface(
                    elevation = 20.dp,
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(bottom = 10.dp, end = 8.dp)
                        .size(30.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.Center)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(top = 5.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
                    .width(123.dp)
                ,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(text = price, fontWeight = FontWeight.Bold)
                Text(
                    text = title,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }
    }


}