package com.gulsenurgunes.furfriends.domain.repository

import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.data.source.remote.model.ProductDetail
import com.gulsenurgunes.furfriends.domain.model.ProductUi

interface ProductRepository {
    suspend fun getProductByCategory(
        store: String,
        category: String
    ): Resource<List<ProductUi>>
    suspend fun getAllProducts(store: String): Resource<List<ProductUi>>
    suspend fun getProductDetail(store: String, id: Int): Resource<ProductDetail>
}