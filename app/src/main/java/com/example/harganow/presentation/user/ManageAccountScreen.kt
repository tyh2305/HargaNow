package com.example.harganow.presentation.user

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.harganow.data.repository.UserRepository
import com.example.harganow.presentation.components.Header
import com.example.harganow.ui.theme.Orange
import kotlinx.coroutines.launch

@Composable
fun Card(title: String, info: String, cardHeight: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .background(Color.White)
            .height(cardHeight.dp)
    ) {
        Column {
            Row {
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                    color = Orange
                )
                Text(
                    text = info,
                    modifier = Modifier
                        .padding(12.dp)
                        .weight(1f),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Light
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

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ManageAccountScreen(
    navigateToPreviousStack: () -> Unit,
    navigateToForgetPassword: () -> Unit,
) {
    var nameDialogOpen by remember { mutableStateOf(false) }
    var nameFieldValue by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    val userRepository: UserRepository = UserRepository()
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    scope.launch {
        loading = true
        userRepository.fetchUser()
        nameFieldValue = userRepository.user.name ?: ""
        loading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Header(
            title = "Manage Account",
            titleSize = 64,
            navigateToPreviousStack = navigateToPreviousStack,

        )
        // TODO: Pass name to info
        Card(title = "Name", "HargaNow", 60,
            onClick = { nameDialogOpen = true })
        Card(
            title = "Change Password", "", 60,
            onClick = navigateToForgetPassword
        )
        if (nameDialogOpen) {
            AlertDialog(
                onDismissRequest = {
                    nameDialogOpen = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            nameDialogOpen = false
                            // TODO: Change name in the database
                            if (nameFieldValue.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Please fill all fields",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                return@TextButton
                            } else {
                                Toast.makeText(
                                    context,
                                    "Username changed successfully",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                userRepository.changeUserName(nameFieldValue)
                            }

                        }
                    ) {
                        Text(text = "Confirm")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            // close the dialog
                            nameDialogOpen = false
                        }
                    ) {
                        Text(text = "Dismiss")
                    }
                },
                title = {
                    Text(text = "Change Name")
                },
                text = {
                    OutlinedTextField(
                        value = nameFieldValue,
                        onValueChange = { nameFieldValue = it },
                        label = { Text("Name") }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                shape = RoundedCornerShape(5.dp),
                backgroundColor = Color.White
            )
        }
    }
}

@Preview
@Composable
fun ManageAccountScreenPreview() {
    ManageAccountScreen(
        navigateToPreviousStack = { /* TODO */ },
        navigateToForgetPassword = { /* TODO */ },
    )
}