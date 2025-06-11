package com.gulsenurgunes.furfriends.data.repository

import com.gulsenurgunes.furfriends.data.mapper.toCartItem
import com.gulsenurgunes.furfriends.data.source.remote.ApiService
import com.gulsenurgunes.furfriends.data.source.remote.model.response.BaseResponse
import com.gulsenurgunes.furfriends.domain.model.AddToCartBody
import com.gulsenurgunes.furfriends.domain.model.ClearCartBody
import com.gulsenurgunes.furfriends.domain.model.DeleteFromCartBody
import com.gulsenurgunes.furfriends.domain.repository.CartRepository
import javax.inject.Inject
import javax.inject.Named

class CartRepositoryImpl @Inject constructor(
    private val api: ApiService,
    @Named("store") private val store: String
) : CartRepository {
    override suspend fun getCartProducts(userId: String) =
        api.getCartProducts(store, userId)
            .products.orEmpty()
            .map { it.toCartItem() }

    override suspend fun addToCart(userId: String, productId: Int): Result<Unit> =
        api.addToCart(store, AddToCartBody(userId, productId))
            .toResult()

    override suspend fun deleteFromCart(userId: String, id: Int): Result<Unit> =
        api.deleteFromCart(store, DeleteFromCartBody(userId, id))
            .toResult()

    override suspend fun clearCart(userId: String): Result<Unit> =
        api.clearCart(store, ClearCartBody(userId))
            .toResult()

    private fun BaseResponse.toResult(): Result<Unit> =
        if (status == 1) Result.success(Unit)
        else Result.failure(IllegalStateException(message))

}
