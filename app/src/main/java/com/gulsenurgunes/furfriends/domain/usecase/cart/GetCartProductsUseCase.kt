package com.gulsenurgunes.furfriends.domain.usecase.cart

import com.gulsenurgunes.furfriends.domain.model.CartItem
import com.gulsenurgunes.furfriends.domain.repository.CartRepository
import javax.inject.Inject

class GetCartProductsUseCase @Inject constructor(
    private val repo: CartRepository
) {
    suspend operator fun invoke(userId: String): List<CartItem> =
        repo.getCartProducts(userId)
}