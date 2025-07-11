package com.gulsenurgunes.furfriends.domain.usecase.favorites

import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.domain.model.FavoriteResponse
import com.gulsenurgunes.furfriends.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(
    private val repo: FavoriteRepository
) {
    suspend operator fun invoke(userId: String, productId: String): Resource<FavoriteResponse> =
        repo.add(userId, productId)
}
