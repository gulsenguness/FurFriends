package com.gulsenurgunes.furfriends.data.source.remote.model.response

import com.google.gson.annotations.SerializedName
import com.gulsenurgunes.furfriends.data.source.remote.model.ProductDto

data class ProductListDto(
    @SerializedName("products")
    val productDto: List<ProductDto>
) : BaseResponse()