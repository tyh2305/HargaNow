import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.harganow.data.auth.FireAuthRepository
import com.example.harganow.presentation.login.AuthViewModel
import com.example.harganow.data.auth.Result

@Composable
fun RegisterScreen(
) {
    val authViewModel: AuthViewModel = viewModel()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginResult = authViewModel.loginResult
    val context = LocalContext.current

    fun handleCallback(result: Result<Unit>): Unit {}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(vertical = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                // TODO : Validate field is not empty
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT)
                        .show()
                    return@Button
                }

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
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Register")
        }

        TextButton(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Already have an account? Log In")
        }
    }
}

