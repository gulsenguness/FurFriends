package com.gulsenurgunes.furfriends.data.repository

import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.common.mapResource
import com.gulsenurgunes.furfriends.data.mapper.mapToProductUi
import com.gulsenurgunes.furfriends.data.mapper.toFavoriteResponse
import com.gulsenurgunes.furfriends.data.safeApiCall
import com.gulsenurgunes.furfriends.data.source.local.FavoritesLocalDataSource
import com.gulsenurgunes.furfriends.data.source.remote.ApiService
import com.gulsenurgunes.furfriends.domain.model.BaseBody
import com.gulsenurgunes.furfriends.domain.model.DeleteFromFavoriteBody
import com.gulsenurgunes.furfriends.domain.model.FavoriteResponse
import com.gulsenurgunes.furfriends.domain.model.ProductUi
import com.gulsenurgunes.furfriends.domain.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val local: FavoritesLocalDataSource
) : FavoriteRepository {

    override suspend fun add(
        userId: String,
        productId: String
    ): Resource<FavoriteResponse> {
        val res = safeApiCall {
            api.addToFavorites(
                store = "",
                baseBody = BaseBody(
                    userId = userId,
                    productId = productId.toInt()
                )
            )
        }.mapResource { it.toFavoriteResponse() }

        if (res is Resource.Success) local.add(productId.toInt())   // ⭐
        return res
    }

    override suspend fun delete(userId: String, productId: String): Resource<FavoriteResponse> {
        val res = safeApiCall {
            api.deleteFromFavorites(
                "", DeleteFromFavoriteBody(userId, productId.toInt())
            )
        }.mapResource { it.toFavoriteResponse() }

        if (res is Resource.Success) local.remove(productId.toInt())
        return res
    }

    override suspend fun clear(userId: String): FavoriteResponse {
        local.clear()
        return FavoriteResponse("Local cleared", 200)
    }

    override suspend fun getFavorites(userId: String): Resource<List<ProductUi>> {
        val localIds = local.getIds()
        return safeApiCall { api.getFavorites("", userId) }
            .mapResource { dto ->
                dto.products.orEmpty()
                    .mapNotNull { it.mapToProductUi() }
                    .map { it.copy(isFavorite = localIds.contains(it.id)) } // ⭐
            }
    }
}