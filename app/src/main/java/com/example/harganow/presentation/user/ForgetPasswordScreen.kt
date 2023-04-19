package com.example.harganow.presentation.user

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.harganow.R
import com.example.harganow.presentation.components.Header
import com.example.harganow.ui.theme.Orange

@Composable
fun ForgetPasswordScreen(
    navigateToPreviousStack: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Header(title = "Forget Password", titleSize = 64, navigateToPreviousStack = navigateToPreviousStack)
        Column(
            modifier = Modifier
                .padding(32.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.forget_password_image),
                contentDescription = "Forget Password Image",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
                    .size(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                placeholder = { Text("Your Email") },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "An verification email will be sent to you.",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Click the link to retrieve your password.",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (email.isEmpty()){
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT)
                            .show()
                        return@Button
                    }
                    // TODO: Sent email to user
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Orange)
            ) {
                Text(
                    text = "SEND",
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun ForgetPasswordScreenPreview() {
    ForgetPasswordScreen(
        navigateToPreviousStack = {},
    )
}