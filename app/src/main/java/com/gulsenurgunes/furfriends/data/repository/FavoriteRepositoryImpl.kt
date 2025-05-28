package com.gulsenurgunes.furfriends.data.repository

import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.common.mapResource
import com.gulsenurgunes.furfriends.data.mapper.mapToProductUi
import com.gulsenurgunes.furfriends.data.mapper.toFavoriteResponse
import com.gulsenurgunes.furfriends.data.safeApiCall
import com.gulsenurgunes.furfriends.data.source.remote.ApiService
import com.gulsenurgunes.furfriends.domain.model.BaseBody
import com.gulsenurgunes.furfriends.domain.model.DeleteFromFavoriteBody
import com.gulsenurgunes.furfriends.domain.model.FavoriteResponse
import com.gulsenurgunes.furfriends.domain.model.ProductUi
import com.gulsenurgunes.furfriends.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val api: ApiService,
) : FavoriteRepository {
    override suspend fun add(
        userId: String,
        productId: String
    ): Resource<FavoriteResponse> =
        safeApiCall {
            api.addToFavorites(
                store = "",
                baseBody = BaseBody(
                    userId = userId,
                    productId = productId.toInt()
                )
            )
        }.mapResource { dto -> dto.toFavoriteResponse() }


    override suspend fun delete(
        userId: String,
        productId: String
    ): Resource<FavoriteResponse> =
        safeApiCall {
            api.deleteFromFavorites(
                store = "",
                deleteFromFavoriteBody = DeleteFromFavoriteBody(
                    userId = userId,
                    id = productId.toInt()
                )
            )
        }.mapResource { it.toFavoriteResponse() }


    override suspend fun clear(userId: String): FavoriteResponse {
        throw NotImplementedError("clearFavorites endpoint’i backend’de yoksa kendi döngünü yaz")
    }

    override suspend fun getFavorites(
        userId: String
    ): Resource<List<ProductUi>> =
        safeApiCall { api.getFavorites(store = "", userId = userId) }
            .mapResource { dto ->
                dto.products
                    .orEmpty()
                    .mapNotNull { it.mapToProductUi() }
            }
}