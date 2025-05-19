package com.gulsenurgunes.furfriends.domain.repository

import com.gulsenurgunes.furfriends.domain.model.FavoriteResponse
import com.gulsenurgunes.furfriends.domain.model.ProductUi

interface FavoriteRepository {
  suspend fun add(userId: String, productId: String): FavoriteResponse
  suspend fun delete(userId: String, productId: String): FavoriteResponse
  suspend fun clear(userId: String): FavoriteResponse
  suspend fun getFavorites(userId: String): List<ProductUi>
}