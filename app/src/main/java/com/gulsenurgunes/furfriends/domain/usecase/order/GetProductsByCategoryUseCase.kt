package com.gulsenurgunes.furfriends.domain.usecase.order

import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.domain.model.ProductUi
import com.gulsenurgunes.furfriends.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(
    private val repo: ProductRepository
) {
    suspend operator fun invoke(
        store: String,
        category: String
    ): Resource<List<ProductUi>> = repo.getProductByCategory(store, category)
}