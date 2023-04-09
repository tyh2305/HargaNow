package com.example.harganow.model

data class User(
    val id: String,
    val name: String,
    val email: String,
)

class UserDAO {
    public fun GetUser(id: String, name: String, email: String): User {
        return User(id, name, email)
    }


}

data class Address(
    val contactName: String,
    val contactNumber: String,
    val unitNumber: String,
    val addressLine1: String,
    val addressLine2: String,
    val poscode: String,
    val city: String,
    val state: String,
)


