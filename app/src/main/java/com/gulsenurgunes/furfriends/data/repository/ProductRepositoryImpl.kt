package com.gulsenurgunes.furfriends.data.repository

import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.common.mapResource
import com.gulsenurgunes.furfriends.data.mapper.mapToProductDetail
import com.gulsenurgunes.furfriends.data.mapper.mapToProductUi
import com.gulsenurgunes.furfriends.data.safeApiCall
import com.gulsenurgunes.furfriends.data.source.remote.ApiService
import com.gulsenurgunes.furfriends.data.source.remote.model.ProductDetail
import com.gulsenurgunes.furfriends.domain.model.ProductUi
import com.gulsenurgunes.furfriends.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ProductRepository {

    override suspend fun getProductByCategory(
        store: String,
        category: String
    ): Resource<List<ProductUi>> =
        safeApiCall {
            api.getProductsByCategory(store, category)
        }.mapResource { dto ->
            dto.products
                .orEmpty()
                .mapNotNull { it.mapToProductUi() }
        }

    override suspend fun getAllProducts(
        store: String
    ): Resource<List<ProductUi>> =
        safeApiCall {
            api.getProducts(store)
        }.mapResource { dto ->
            dto.products
                .orEmpty()
                .mapNotNull { it.mapToProductUi() }
        }

    override suspend fun getProductDetail(store: String, id: Int): Resource<ProductDetail> =
        safeApiCall {
            api.getProductDetail(store, id)
        }.mapResource { response ->
            response.productDto?.mapToProductDetail(isFavorite = false)
                ?: throw Exception("Ürün bulunamadı")
        }

}