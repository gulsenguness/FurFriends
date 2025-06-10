package com.gulsenurgunes.furfriends.domain.usecase.favorites

import com.gulsenurgunes.furfriends.data.source.local.FavoritesLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFavoriteIdsUseCase @Inject constructor(
    private val local: FavoritesLocalDataSource
) {
    operator fun invoke(): Flow<Set<Int>> = local.idsFlow
}