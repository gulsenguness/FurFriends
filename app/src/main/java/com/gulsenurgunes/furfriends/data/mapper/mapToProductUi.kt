package com.gulsenurgunes.furfriends.data.mapper

import com.gulsenurgunes.furfriends.data.source.remote.model.CategoryDto
import com.gulsenurgunes.furfriends.data.source.remote.model.ProductDetail
import com.gulsenurgunes.furfriends.data.source.remote.model.ProductDto
import com.gulsenurgunes.furfriends.data.source.remote.model.response.BaseResponse
import com.gulsenurgunes.furfriends.domain.model.Category
import com.gulsenurgunes.furfriends.domain.model.FavoriteResponse
import com.gulsenurgunes.furfriends.domain.model.ProductUi


fun ProductDto.mapToProductUi(): ProductUi? {
    return this.id?.let {
        ProductUi(
            category = this.category.orEmpty(),
            count = this.count ?: 0,
            description = this.description.orEmpty(),
            id = it,
            imageOne = this.imageOne.orEmpty(),
            price = this.price ?: 0.0,
            title = this.title.orEmpty(),
            isFavorite = false,
            rate = this.rate ?: 0.0,
            salePrice = this.salePrice ?: 0.0,
            saleState = this.saleState ?: false,
            imageThree = this.imageThree.orEmpty(),
            imageTwo = this.imageTwo.orEmpty()
        )
    }
}

fun ProductDto.mapToProductDetail(isFavorite: Boolean): ProductDetail {
    return ProductDetail(
        description = this.description.orEmpty(),
        id = this.id,
        imageOne = this.imageOne.orEmpty(),
        price = this.price,
        title = this.title.orEmpty(),
        isFavorite = isFavorite,
        rate = this.rate,
        salePrice = this.salePrice,
        saleState = this.saleState,
        imageThree = this.imageThree.orEmpty(),
        imageTwo = this.imageTwo.orEmpty(),
    )
}

fun CategoryDto.toDomain(): Category =
    Category(
        id= this.id ?: 0,
        name = this.name.orEmpty(),
        imageUrl = this.imageUrl.orEmpty()
    )

fun BaseResponse.toFavoriteResponse(): FavoriteResponse {
    return FavoriteResponse(
        message = message.orEmpty(),
        status = status ?: -1
    )
}
