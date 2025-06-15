package com.gulsenurgunes.furfriends.ui.mycart

import com.gulsenurgunes.furfriends.data.source.remote.model.response.BaseResponse
import com.gulsenurgunes.furfriends.domain.model.CartItem

object MyCartContract {
    data class UiState(
        val isLoading: Boolean = false,
        val items: List<CartItem> = emptyList(),
        val totalPrice: Double = 0.0,
        val clearCartResponse: BaseResponse? = null,
        val errorMessage: String? = null
    )

    sealed class UiAction {
        data object LoadCart : UiAction()
        data class RemoveItemClick(val itemId: Int) : UiAction()
        data object ClearCartClick : UiAction()
        data object CheckoutClick : UiAction()
        data object BackButtonClick : UiAction()
        data class DeleteItem(val id: Int) : UiAction()
    }

    sealed class UiEffect {
        data class ShowToastMessage(val message: String) : UiEffect()
        data object NavigateBack : UiEffect()
        data object NavigateCheckout : UiEffect()
        data object ShowEmptyState : UiEffect()
    }
}