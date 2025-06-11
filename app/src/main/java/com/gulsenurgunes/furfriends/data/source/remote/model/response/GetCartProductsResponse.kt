package com.gulsenurgunes.furfriends.data.source.remote.model.response

import com.gulsenurgunes.furfriends.data.source.remote.model.ProductDto

data class GetCartProductsResponse(
    val products: List<ProductDto>? = emptyList(),
    val status: Int,
    val message: String
)