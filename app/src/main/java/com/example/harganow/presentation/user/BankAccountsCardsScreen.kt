package com.example.harganow.presentation.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harganow.R
import com.example.harganow.presentation.components.Header
import com.example.harganow.ui.theme.Orange

@Composable
fun SelectionCard(title: String, cardHeight:Int, iconID:Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Column {
            Row {
                Icon(
                    painter = painterResource(iconID),
                    contentDescription = title,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(64.dp)

                )
                Text(
                    text = title,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp,
                    color = Orange
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
fun BankAccountsCardsScreen(
    navigateToPreviousStack: () -> Unit,
    navigateToAddCard: () -> Unit,
    navigateToAddBankAccount: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Header(title = "Bank Accounts/ Cards", titleSize = 64, navigateToPreviousStack = navigateToPreviousStack)
        SelectionCard(title = "+ Add New Card", cardHeight = 80 , iconID = R.drawable.credit_card_black_24dp, navigateToAddCard)
        SelectionCard(title = "+ Add Bank Account", cardHeight = 80 , iconID = R.drawable.credit_card_black_24dp, navigateToAddBankAccount)
    }
}

@Preview
@Composable
fun BankAccountsCardsScreenPreview() {
    BankAccountsCardsScreen(
        navigateToPreviousStack = { /* TODO */ },
        navigateToAddCard = {},
        navigateToAddBankAccount = {}
    )
}