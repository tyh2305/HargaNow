package com.example.harganow.domain.model

data class DataOrException<T, E : Exception?>(
    var data: T? = null,
    var exception: E? = null
)
