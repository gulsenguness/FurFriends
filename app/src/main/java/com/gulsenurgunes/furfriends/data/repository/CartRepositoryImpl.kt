package com.gulsenurgunes.furfriends.data.repository

import com.gulsenurgunes.furfriends.data.mapper.toCartItem
import com.gulsenurgunes.furfriends.data.source.remote.ApiService
import com.gulsenurgunes.furfriends.domain.model.AddToCartBody
import com.gulsenurgunes.furfriends.domain.model.CartItem
import com.gulsenurgunes.furfriends.domain.model.ClearCartBody
import com.gulsenurgunes.furfriends.domain.model.DeleteFromCartBody
import com.gulsenurgunes.furfriends.domain.repository.CartRepository
import javax.inject.Inject
import javax.inject.Named

abstract class CartRepositoryImpl @Inject constructor(
    private val api: ApiService,
    @Named("store") private val store: String
) : CartRepository {
    override suspend fun getCartProducts(userId: String): List<CartItem> =
        api.getCartProducts(store, userId)
            .products
            .map { it.toCartItem() }

    override suspend fun addToCart(userId: String, productId: Int): Result<Unit> =
        runCatching {
            val body =AddToCartBody(userId, productId)
            api.addToCart(store, body)
        }

    override suspend fun deleteFromCart(userId: String, id: Int): Result<Unit> =
        runCatching {
            val body =DeleteFromCartBody(userId, id)
            api.deleteFromCart(store, body)
        }

    override suspend fun clearCart(userId: String): Result<Unit> =
        runCatching {
            val body = ClearCartBody(userId)
            api.clearCart(store, body)
        }
}
