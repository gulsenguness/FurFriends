package com.gulsenurgunes.furfriends.domain.usecase.cart

import com.gulsenurgunes.furfriends.domain.repository.CartRepository
import javax.inject.Inject

class DeleteCartItemUseCase @Inject constructor(
    private val repo: CartRepository
) {
    suspend operator fun invoke(userId: String, itemId: Int): Result<Unit> =
        repo.deleteFromCart(userId, itemId)
}