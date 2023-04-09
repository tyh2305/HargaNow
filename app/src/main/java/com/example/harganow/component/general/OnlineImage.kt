package com.example.harganow.component.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Preview
@Composable
fun OnlineImage(
    imageUrl: String = "https://cdn-icons-png.flaticon.com/512/2991/2991148.png"
) {
    Image(
        painter = rememberImagePainter(data = imageUrl),
        contentDescription = "Online image",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Adjust height as per your requirement
    )
}

