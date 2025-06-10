package com.gulsenurgunes.furfriends.domain.usecase.order

import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.data.source.remote.model.ProductDetail
import com.gulsenurgunes.furfriends.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(store: String, productId: Int): Resource<ProductDetail> {
        return repository.getProductDetail(store, productId)
    }
}
