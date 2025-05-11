package com.gulsenurgunes.furfriends.data.source.remote.model

import com.gulsenurgunes.furfriends.data.source.remote.model.response.BaseResponse

data class CheckFavoriteResponse(
    val isFavorite: Boolean
) : BaseResponse()