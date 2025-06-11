package com.gulsenurgunes.furfriends.domain.usecase.cart

import com.gulsenurgunes.furfriends.domain.repository.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repo: CartRepository
) {
    suspend operator fun invoke(userId: String, productId: Int): Result<Unit> =
        repo.addToCart(userId, productId)
}