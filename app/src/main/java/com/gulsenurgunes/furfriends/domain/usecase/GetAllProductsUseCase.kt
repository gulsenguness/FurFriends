package com.gulsenurgunes.furfriends.domain.usecase

import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.domain.model.ProductUi
import com.gulsenurgunes.furfriends.domain.repository.ProductRepository
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val repo: ProductRepository
) {
    suspend operator fun invoke(store: String): Resource<List<ProductUi>> =
        repo.getAllProducts(store)
}