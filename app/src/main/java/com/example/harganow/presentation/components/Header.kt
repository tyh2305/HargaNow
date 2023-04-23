package com.example.harganow.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.harganow.ui.theme.Orange

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800)
@Composable
fun HeaderPreview() {
    Header(title = "Reset Password", titleSize = 64, navigateToPreviousStack = {})
}

@Composable
fun Header(title: String, titleSize: Int, navigateToPreviousStack: () -> Unit) {
    Column() {
        TopAppBar(
            modifier = Modifier
                .height(75.dp),
            backgroundColor = Color.White,
            title = {
                Text(
                    text = title,
                    modifier = Modifier.padding(horizontal = titleSize.dp),
                    textAlign = TextAlign.Center,
                )
            } ,
            navigationIcon = {
                Button(
                    onClick = {
                        navigateToPreviousStack()
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Orange)
                ) {
                    Text(
                        text = "<",
                        color = Color.White,
                    )
                }
            },
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(Color.Black.copy(alpha = 0.2f)))
    }
}