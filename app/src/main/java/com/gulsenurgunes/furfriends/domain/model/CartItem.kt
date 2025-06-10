package com.gulsenurgunes.furfriends.domain.model

data class CartItem(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val price: Double,
    val salePrice: Double?,
    val quantity: Int
)