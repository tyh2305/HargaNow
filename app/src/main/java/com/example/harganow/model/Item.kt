package com.example.harganow.model

import com.example.harganow.utils.ImageGetter

data class Item(
    var id: String,
    val name: String,
    val unit: String,
    val group: String,
    val category: String,
    val img: String = ImageGetter.GetImage(id),
)

