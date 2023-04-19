package com.example.harganow.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harganow.R
import com.example.harganow.ui.theme.Orange

@Composable
fun HomeScreen(
    navigateToLogin: () -> Unit,
    navigateToRegister: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "HargaNow", color = Orange, fontWeight = FontWeight.Bold, fontSize = 28.sp)

        Spacer(modifier = Modifier.padding(64.dp))

        Image(
            painter = painterResource(id = R.drawable.home_image1),
            contentDescription = "Login Image",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .size(200.dp)
        )

        Spacer(modifier = Modifier.padding(45.dp))

        Text(text = "Price Checking", color = Orange, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Text(text = "Compare price & save money with us", color = Orange)

        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick = {
                navigateToLogin()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Orange)
        ) {
            Text(text = "Login In", color = Color.White)
        }

        Button(
            onClick = {
                navigateToRegister()
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Orange)
        ) {
            Text(text = "Sign Up", color = Color.White)
        }
    }


}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navigateToLogin = { /* TODO */},
        navigateToRegister = { /* TODO */ },
    )
}