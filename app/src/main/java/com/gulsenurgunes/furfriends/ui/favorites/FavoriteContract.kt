package com.gulsenurgunes.furfriends.ui.favorites

import com.gulsenurgunes.furfriends.domain.model.FavoriteResponse
import com.gulsenurgunes.furfriends.domain.model.ProductUi

object FavoriteContract {
    data class UiState(
        val isLoading: Boolean = false,
        val favoriteProducts: List<ProductUi> = emptyList(),
        val deleteFromFavorites: FavoriteResponse? = null,
        val addToFavorites:    FavoriteResponse? = null,
        val errorMessage: String? = null
    )

    sealed class UiAction {
        data class DeleteFromFavorites(val productId: Int) : UiAction()
        data class LoadFavorites(val favoriteProducts: List<ProductUi>) : UiAction()
        data class AddToFavorites(val productId: Int)    : UiAction()
        data class AddToCart(val product: ProductUi) : UiAction()
    }

    sealed class UiEffect {
        data class ShowError(val message: String) : UiEffect()
        data class FavoriteProductDetailClick(val productId: Int) : UiEffect()
        data object BackScreen : UiEffect()
    }
}
