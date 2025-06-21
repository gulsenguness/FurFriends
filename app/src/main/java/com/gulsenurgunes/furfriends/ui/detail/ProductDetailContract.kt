package com.gulsenurgunes.furfriends.ui.detail

import com.gulsenurgunes.furfriends.data.source.remote.model.ProductDetail
import com.gulsenurgunes.furfriends.data.source.remote.model.response.BaseResponse

object ProductDetailContract {
    data class UiState(
        val isLoading: Boolean = false,
        val productDetail: ProductDetail? = null,
        val addToCart: BaseResponse? = null,
        val errorMessage: String? = null,
    )

    sealed class UiAction {
        data class ToggleFavoriteClick(val product: ProductDetail) : UiAction()
        data class AddToCartClick(val productDetail: ProductDetail) : UiAction()
        data object BackButtonClick : UiAction()
        data object ShareProduct : UiAction()
    }

    sealed class UiEffect {
        data object ShowError : UiEffect()
        data object BackScreen : UiEffect()
        data class ShowToastMessage(val message: String) : UiEffect()
        data object NavigateBack : UiEffect()
        data class ShareProduct(val shareText: String) : UiEffect()
        data class ShowAlreadyInCartMessage(val message: String) : UiEffect()
        data object NavigateToCart : UiEffect()
    }
}
