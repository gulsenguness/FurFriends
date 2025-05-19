package com.gulsenurgunes.furfriends.data.repository

import com.gulsenurgunes.furfriends.data.mapper.mapToProductUi
import com.gulsenurgunes.furfriends.data.mapper.toFavoriteResponse
import com.gulsenurgunes.furfriends.data.source.remote.ApiService
import com.gulsenurgunes.furfriends.domain.model.BaseBody
import com.gulsenurgunes.furfriends.domain.model.DeleteFromFavoriteBody
import com.gulsenurgunes.furfriends.domain.model.FavoriteResponse
import com.gulsenurgunes.furfriends.domain.model.ProductUi
import com.gulsenurgunes.furfriends.domain.repository.FavoriteRepository

class FavoriteRepositoryImpl(
    private val api: ApiService,
    private val store: String
) : FavoriteRepository {

    override suspend fun add(userId: String, productId: String): FavoriteResponse {
        val body = BaseBody(
            userId = userId,
            productId = productId.toInt()
        )
        val resp = api.addToFavorites(store, body)
        return resp.toFavoriteResponse()
    }

    override suspend fun delete(userId: String, productId: String): FavoriteResponse {
        val body = DeleteFromFavoriteBody(
            userId = userId,
            id = productId.toInt()
        )
        val resp = api.deleteFromFavorites(store, body)
        return resp.toFavoriteResponse()
    }


    override suspend fun clear(userId: String): FavoriteResponse {
        throw NotImplementedError("clearFavorites endpoint’i backend’de yoksa kendi döngünü yaz")
    }

    override suspend fun getFavorites(userId: String): List<ProductUi> {
        val dto = api.getFavorites(store, userId)
        return dto.products
            .mapNotNull { it.mapToProductUi() }
    }
}