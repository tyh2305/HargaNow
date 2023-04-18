package com.example.harganow.domain.model

data class ItemPrice(
    var item: Item,
    var premise: Premise,
    var price: Double,
    var date: ItemDate,
)
