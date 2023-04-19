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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.harganow.ui.theme.Orange

@Composable
fun ResetPasswordScreen(
    navigateToPreviousStack: () -> Unit,
    navigateToForgetPassword: () -> Unit,
) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Header(title = "Reset Password", titleSize = 64, navigateToPreviousStack = navigateToPreviousStack)
        Column(
            modifier = Modifier
                .padding(32.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = newPassword,
                onValueChange = {newPassword = it},
                placeholder = { Text("New Password") },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {confirmPassword = it},
                placeholder = { Text("Confirm Password") },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (newPassword.isEmpty() || confirmPassword.isEmpty()){
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT)
                            .show()
                        return@Button
                    }

                    if(confirmPassword == newPassword) {
                        // TODO : Reset password
                    } else {
                        Toast.makeText(context, "Confirm password do not match your password", Toast.LENGTH_SHORT)
                            .show()
                        return@Button
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Orange)
            ) {
                Text(
                    text = "RESET",
                    color = Color.White
                )
            }
            
            TextButton(onClick = { navigateToForgetPassword() }) {
                Text(
                    text = "Forget Your Password?",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Orange
                )
            }
        }
    }
}

@Preview
@Composable
fun ResetPasswordScreenPreview() {
    ResetPasswordScreen(
        navigateToPreviousStack = {},
        navigateToForgetPassword = {},
    )
}