package com.gulsenurgunes.furfriends.domain.repository

import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.domain.model.ProductUi

interface ProductRepository {
    suspend fun getProductByCategory(
        store: String,
        category: String
    ): Resource<List<ProductUi>>
}