package com.example.harganow.presentation.login

import androidx.lifecycle.ViewModel
import com.example.harganow.data.auth.FireAuthRepository
import com.example.harganow.data.repository.UserRepository
import com.example.harganow.domain.model.AppUser
import com.example.harganow.data.auth.Result as Result

class AuthViewModel : ViewModel() {
    private val authRepository: FireAuthRepository = FireAuthRepository()
    private val userRepository: UserRepository = UserRepository()

    var loginResult: Result<Unit>? = null

    suspend fun login(email: String, password: String) {
        authRepository.login(email, password) { result ->
            loginResult = result
        }
    }

    suspend fun register(
        email: String,
        name: String?,
        password: String,
        onResult: (Result<Any>) -> Unit
    ) {
        authRepository.register(email, password) { result ->
            onResult(result)
            if (result is Result.Success) {
                userRepository.createNewUser(
                    AppUser(
                        id = result.data.toString(),
                        name = name,
                        email = email
                    )
                )
            }
        }
    }

    suspend fun sendResetPassword(email:String, onResult: (Result<Unit>) -> Unit) {
        authRepository.resetPassword(email) { result ->
            onResult(result)
        }
    }

    fun logout(onResult: (Result<Unit>) -> Unit) {
        authRepository.logout { result ->
            onResult(result)
        }
    }

    fun getCurrentUser(): String? {
        return authRepository.getCurrentUser()?.email
    }
}
