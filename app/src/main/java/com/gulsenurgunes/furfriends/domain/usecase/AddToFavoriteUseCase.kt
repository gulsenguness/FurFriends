package com.gulsenurgunes.furfriends.domain.usecase

import com.gulsenurgunes.furfriends.domain.model.FavoriteResponse
import com.gulsenurgunes.furfriends.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(
    private val repo: FavoriteRepository
) {
    suspend operator fun invoke(userId:String,productId:String): FavoriteResponse=
        repo.add(userId,productId)
}
