package com.gulsenurgunes.furfriends.data.source.remote.model.response

import com.google.gson.annotations.SerializedName
import com.gulsenurgunes.furfriends.data.source.remote.model.ProductDto

data class GetCartProductResponse(
    @SerializedName("products")
    val productDto: List<ProductDto>?
) : BaseResponse()