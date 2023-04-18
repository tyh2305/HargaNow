package com.example.harganow.presentation.login

import androidx.lifecycle.ViewModel
import com.example.harganow.data.auth.FireAuthRepository
import com.example.harganow.data.auth.Result as Result

class AuthViewModel : ViewModel() {
    private val authRepository: FireAuthRepository = FireAuthRepository()

    var loginResult: Result<Unit>? = null

    fun login(email: String, password: String) {
        authRepository.login(email, password) { result ->
            loginResult = result
        }
    }

    fun register(email: String, password: String, onResult: (Result<Unit>) -> Unit) {
        authRepository.register(email, password) { result ->
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