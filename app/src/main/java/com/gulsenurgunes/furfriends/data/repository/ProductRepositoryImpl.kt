package com.gulsenurgunes.furfriends.data.repository

import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.data.mapper.mapToProductUi
import com.gulsenurgunes.furfriends.data.source.remote.ApiService
import com.gulsenurgunes.furfriends.domain.model.ProductUi
import com.gulsenurgunes.furfriends.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ProductRepository {

    override suspend fun getProductByCategory(
        store: String,
        category: String
    ): Resource<List<ProductUi>> {
        return try {
            val resp = api.getProductsByCategory(store, category)
            val list = resp.products
                .mapNotNull {
                    it.mapToProductUi()
                }
            Resource.Success(list)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Ürünler yüklenirken hata oluştu")
        }
    }
}