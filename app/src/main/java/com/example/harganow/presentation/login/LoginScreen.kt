import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.harganow.data.auth.Result
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.harganow.R
import com.example.harganow.presentation.login.AuthViewModel

@Composable
fun LoginScreen(
    navigateToPreviousStack: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToMain: () -> Unit
) {
    val authViewModel: AuthViewModel = viewModel()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginResult = authViewModel.loginResult
    val context = LocalContext.current

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
            painter = painterResource(id = R.drawable.login_image),
            contentDescription = "Login Image",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
                .size(300.dp)
        )

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

        TextButton(
            onClick = { /* TODO: Reset password */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Forget Password?"
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // TODO : Validate field is not empty
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT)
                        .show()
                    return@Button
                }

                authViewModel.login(email, password)
                authViewModel.loginResult?.let {
                    if (it is Result.Success) {
                        Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT)
                            .show()
                        navigateToMain()
                    } else {
//                      TODO: Check whether account is register
                        Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Log In")
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
                // TODO : Navigate to sign up screen
                navigateToRegister()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sign Up")
        }
    }
}

