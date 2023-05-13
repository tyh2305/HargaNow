package com.example.harganow.presentation.user

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.harganow.R
import com.example.harganow.data.auth.Result
import com.example.harganow.presentation.components.Header
import com.example.harganow.presentation.login.AuthViewModel
import com.example.harganow.ui.theme.Orange
import kotlinx.coroutines.launch

@Composable
fun SettingsCard(title: String, cardHeight: Int, iconID: Int, onClick: () -> Unit) {
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.Black.copy(alpha = 0.2f))
            )
        }
    }
}

@Composable
fun UserSettingsScreen(
    navigateToPreviousStack: () -> Unit,
    navigateToManageAccount: () -> Unit,
    navigateToAddress: () -> Unit,
    navigateToBankAccount: () -> Unit,
    navigateToPolicy: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val authViewModel: AuthViewModel = viewModel()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    fun CallLogout() {
        coroutineScope.launch {
            authViewModel.logout() { result ->
                when (result) {
                    is Result.Success -> {
                        // Handle successful registration
                        Toast.makeText(context, "Logout Success", Toast.LENGTH_SHORT)
                            .show()
                        navigateToHome()
                    }

                    is Result.Failure -> {
                        // Handle registration error
                        Toast.makeText(context, "Logout Failed", Toast.LENGTH_SHORT)
                            .show()
                    }

                    else -> {}
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Header(
            title = "User Settings",
            titleSize = 64,
            navigateToPreviousStack = navigateToPreviousStack,
            false
        )
        SettingsCard(
            title = "Manage Account", 60,
            R.drawable.manage_accounts_black_24dp, onClick = navigateToManageAccount
        )
        SettingsCard(
            title = "Policy", 60,
            R.drawable.policy_black_24dp, onClick = navigateToPolicy
        )
        SettingsCard(title = "Logout", 60,
            R.drawable.logout_black_24dp, onClick = { CallLogout() })
    }
}


@Preview
@Composable
fun UserSettingsScreenPreview() {
    UserSettingsScreen(
        navigateToPreviousStack = {},
        navigateToManageAccount = {},
        navigateToAddress = {},
        navigateToBankAccount = {},
        navigateToPolicy = {},
        navigateToHome = {},
    )
}