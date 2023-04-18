package com.example.harganow.domain.model

data class AppUser(
    val id: String,
    val name: String? = "Anonymous",
    val email: String? = "no-reply@harganow.com",
)



