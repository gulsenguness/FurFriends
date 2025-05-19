package com.gulsenurgunes.furfriends.domain.usecase

import com.gulsenurgunes.furfriends.domain.model.ProductUi
import com.gulsenurgunes.furfriends.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
  private val repo: FavoriteRepository
) {
  suspend operator fun invoke(userId: String): List<ProductUi> =
    repo.getFavorites(userId)
}