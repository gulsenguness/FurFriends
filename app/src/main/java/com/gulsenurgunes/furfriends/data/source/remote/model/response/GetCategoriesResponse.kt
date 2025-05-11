package com.gulsenurgunes.furfriends.data.source.remote.model.response

import com.gulsenurgunes.furfriends.data.source.remote.model.CategoryDto

data class GetCategoriesResponse(
    val categories: List<CategoryDto>
) : BaseResponse()