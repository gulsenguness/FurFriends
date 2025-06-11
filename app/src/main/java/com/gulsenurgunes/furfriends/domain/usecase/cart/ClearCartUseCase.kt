package com.gulsenurgunes.furfriends.domain.usecase.cart

import com.gulsenurgunes.furfriends.domain.repository.CartRepository
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(
    private val repo: CartRepository
) {
    suspend operator fun invoke(userId: String): Result<Unit> =
        repo.clearCart(userId)
}