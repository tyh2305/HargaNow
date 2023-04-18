package com.example.harganow.data.auth

import com.example.harganow.domain.model.AppUser


interface AuthRepository {
    val isLoggedIn: Boolean
    val currentUser: AppUser?

    suspend fun login(email: String, password: String, callback: (Result<Unit>) -> Unit)
    suspend fun register(email: String, password: String, callback: (Result<Unit>)): Result<Unit>
    suspend fun logout(): Result<Unit>
}