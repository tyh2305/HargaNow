package com.example.harganow.presentation.user

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.harganow.presentation.components.Header
import com.example.harganow.ui.theme.Orange

@Composable
fun VerifyPasswordScreen(
    navigateToPreviousStack: () -> Unit,
    navigateToResetPassword: () -> Unit,
) {
    var currentPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Header(title = "Verify Password", titleSize = 64, navigateToPreviousStack = navigateToPreviousStack)
        Column(
            modifier = Modifier
                .padding(32.dp)
        ) {
            Text(
                text ="Keep you account safe, please verify your identity by entering your password",
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = currentPassword,
                onValueChange = {currentPassword = it},
                placeholder = {Text("Current Password")},
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // TODO : Validate field is not empty
                    if (currentPassword.isEmpty()) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT)
                            .show()
                        return@Button
                    }

                    // TODO : Change to real password
                    if(currentPassword == "123456") {
                        navigateToResetPassword()
                    } else {
                        Toast.makeText(context, "Password is incorrect", Toast.LENGTH_SHORT)
                            .show()
                        return@Button
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Orange)
            ) {
                Text(
                    text = "CONTINUE",
                    color = Color.White
                    )
            }
        }
    }
}

@Preview
@Composable
fun VerifyPasswordScreenPreview() {
    VerifyPasswordScreen(
        navigateToPreviousStack = {},
        navigateToResetPassword = {},
    )
}