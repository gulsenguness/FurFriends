package com.gulsenurgunes.furfriends.domain.repository

import com.gulsenurgunes.furfriends.domain.model.CartItem

interface CartRepository {
    suspend fun getCartProducts(userId: String): List<CartItem>
    suspend fun addToCart(userId:String,productId:Int):Result<Unit>
    suspend fun deleteFromCart(userId:String,id:Int):Result<Unit>
    suspend fun clearCart(userId:String):Result<Unit>
}