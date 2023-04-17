package com.example.harganow.model

data class Address(
    val contact: Contact,
    val unitNumber: String,
    val addressLine1: String,
    val addressLine2: String,
    val poscode: String,
    val city: String,
    val state: String,
)

