package com.example.harganow.presentation.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.harganow.R
import com.example.harganow.ui.theme.Orange

@Composable
fun Card(title:String, info:String, cardHeight: Int, onClick: () -> Unit)
{
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .background(Color.White)
            .height(cardHeight.dp)
    ) {
        Column {
            Row {
                Text(text = title,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                    color = Orange
                )
                Text(text = info,
                    modifier = Modifier
                        .padding(12.dp)
                        .weight(1f),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Light
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.Black.copy(alpha = 0.2f)))
        }
    }
}

@Composable
fun ManageAccountScreen(
    navigateToHome: () -> Unit,
    navigateToName: () -> Unit,
    navigateToEmail: () -> Unit,
    navigateToPassword: () -> Unit,
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Header(title = "Manage Account", titleSize = 64, navigateToHome = navigateToHome)
        Card(title = "Name", "HargaNow", 60,
            onClick = navigateToName)
        Card(title = "Email", "harganow@example.com",60,
            onClick = navigateToEmail)
        Card(title = "Change Password", "",60,
            onClick = navigateToPassword)
    }
}

@Preview
@Composable
fun ManageAccountScreenPreview() {
    ManageAccountScreen(
        navigateToHome = { /* TODO */ },
        navigateToName = { /* TODO */ },
        navigateToEmail = { /* TODO */ },
        navigateToPassword = { /* TODO */ },
    )
}