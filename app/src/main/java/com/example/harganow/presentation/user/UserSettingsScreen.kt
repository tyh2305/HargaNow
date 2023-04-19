package com.example.harganow.presentation.user

import android.graphics.drawable.Icon
import android.view.animation.PathInterpolator
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harganow.R
import com.example.harganow.ui.theme.Orange


@Composable
fun Header(title: String, titleSize: Int,navigateToHome: () -> Unit) {
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
                        navigateToHome()
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

@Composable
fun Card(cardHeight: Int, onClick: () -> Unit)
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

            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.Black.copy(alpha = 0.2f)))
        }
    }
}

@Composable
fun SettingsCard(title: String, cardHeight:Int, iconID:Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .background(Color.White)
            .height(cardHeight.dp)
    ) {
        Column {
            Row {
                Icon(
                    painter = painterResource(iconID),
                    contentDescription = title,
                    modifier = Modifier.padding(16.dp),
                )
                Text(
                    text = title,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp,
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
fun UserSettingsScreen(
    navigateToManageAccount: () -> Unit,
    navigateToEmail: () -> Unit,
    navigateToBankAccount: () -> Unit,
    navigateToPolicy: () -> Unit,
    navigateToHome: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Header(title = "User Settings", titleSize = 64, navigateToHome = navigateToHome)
        SettingsCard(title = "Manage Account", 60,
            R.drawable.manage_accounts_black_24dp,  onClick = navigateToManageAccount)
        SettingsCard(title = "Edit Address",60,
            R.drawable.location_on_black_24dp, onClick = navigateToManageAccount)
        SettingsCard(title = "Bank Accounts / Cards", 60,
            R.drawable.account_balance_wallet_black_24dp, onClick = navigateToManageAccount)
        SettingsCard(title = "Policy", 60,
            R.drawable.policy_black_24dp, onClick = navigateToManageAccount)
        SettingsCard(title = "Logout", 60,
            R.drawable.logout_black_24dp, onClick = navigateToManageAccount)
    }
}


@Preview
@Composable
fun UserSettingsScreenPreview()
{
    UserSettingsScreen(
        navigateToManageAccount = {},
        navigateToEmail = {},
        navigateToBankAccount = {},
        navigateToPolicy = {},
        navigateToHome = {},
    )
}