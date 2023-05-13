package com.example.harganow.presentation.user

import android.provider.Telephony.Mms.Addr
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.harganow.domain.model.Address
import com.example.harganow.presentation.components.Header
import com.example.harganow.ui.theme.Orange


@Composable
fun AddressScreen(
    navigateToPreviousStack: () -> Unit,
    address: MutableState<Address>
) {
    var unitNumber by remember { mutableStateOf("") }
    var addressLine1 by remember { mutableStateOf("") }
    var addressLine2 by remember { mutableStateOf("") }
    var postcode by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Header(
            title = "Enter Your Address",
            titleSize = 64,
            navigateToPreviousStack = navigateToPreviousStack
        )
        Column(
            modifier = Modifier
                .padding(32.dp)
        ) {
            OutlinedTextField(
                value = unitNumber,
                onValueChange = { unitNumber = it },
                label = { Text("Unit Number") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = addressLine1,
                onValueChange = { addressLine1 = it },
                label = { Text("Address Line 1") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = addressLine2,
                onValueChange = { addressLine2 = it },
                label = { Text("Address Line 2") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = postcode,
                onValueChange = {
                    if (it.length <= 5) postcode = it
                },
                label = { Text("Postcode") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                ),
            )

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("City") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state,
                onValueChange = { state = it },
                label = { Text("State") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (unitNumber.isEmpty() || addressLine1.isEmpty() || addressLine2.isEmpty() || postcode.isEmpty() || city.isEmpty() || state.isEmpty()) {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT)
                            .show()
                        return@Button
                    }
                    address.value = Address(
                        null,
                        unitNumber, addressLine1, addressLine2, postcode, city, state
                    )
                    navigateToPreviousStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Orange)
            ) {
                Text(text = "Proceed", color = Color.White)
            }
        }
    }
}