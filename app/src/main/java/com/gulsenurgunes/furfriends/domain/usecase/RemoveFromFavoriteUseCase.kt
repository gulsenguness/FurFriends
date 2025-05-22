package com.gulsenurgunes.furfriends.domain.usecase

import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.domain.model.FavoriteResponse
import com.gulsenurgunes.furfriends.domain.repository.FavoriteRepository
import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(
    private val repo: FavoriteRepository
) {
    suspend operator fun invoke(userId: String, productId: String): Resource<FavoriteResponse> =
        repo.delete(userId, productId)
}

