package com.example.harganow.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.harganow.R
import com.example.harganow.ui.theme.Orange

@Preview
@Composable
fun BackButtonPreview() {
    BackButton(navigateToPreviousStack = {})
}

@Composable
fun BackButton(navigateToPreviousStack: () -> Unit) {
    Button(
        onClick = {
            navigateToPreviousStack()
        },
        modifier = Modifier
            .padding(16.dp)
            .size(40.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Orange),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_48),
            contentDescription = "Back",
            Modifier.fillMaxSize(0.5f),
            tint = Color.White,
        )
    }
}