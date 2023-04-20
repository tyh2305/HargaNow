import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.harganow.R
import com.example.harganow.data.auth.Result
import com.example.harganow.presentation.login.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    navigateToPreviousStack: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val authViewModel: AuthViewModel = viewModel()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginResult = authViewModel.loginResult
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    fun handleCallback(result: Result<Unit>): Unit {}

    fun CallRegister() {
        coroutineScope.launch {
            authViewModel.register(email, password) { result ->
                when (result) {
                    is Result.Success -> {
                        // Handle successful registration
                        Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT)
                            .show()
                    }

                    is Result.Failure -> {
                        // Handle registration error
                        Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                navigateToPreviousStack()
            },
            modifier = Modifier
                .padding(vertical = 8.dp),
        ) {
            Text(
                text = "<",
            )
        }

        // TODO: Change the size of the image
        Image(
            painter = painterResource(id = R.drawable.signup_image),
            contentDescription = "Sign Up Image",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
                .size(250.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Name") },
            leadingIcon = { Icon(painterResource(id = R.drawable.person_black_24dp), contentDescription = "Email") },
            placeholder = { Text("Your Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = { Icon(painterResource(id = R.drawable.email_black_24dp), contentDescription = "Email") },
            placeholder = { Text("harganow@example.com") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(painterResource(id = R.drawable.lock_black_24dp), contentDescription = "Password") },
            placeholder = { Text("Your Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(45.dp))

        Button(
            onClick = {
                // TODO : Validate field is not empty
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT)
                        .show()
                    return@Button
                }
                CallRegister()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sign Up")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Divider(
                thickness = 3.dp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "or",
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Divider(
                thickness = 3.dp,
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedButton(
            onClick = {
                navigateToLogin()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Log In")
        }
    }
}
@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        navigateToPreviousStack = { /* TODO */ },
        navigateToLogin = { /* TODO */ }
    )
}

