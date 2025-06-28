package com.gulsenurgunes.furfriends.ui.categoryfull.categorygroup

import com.gulsenurgunes.furfriends.domain.model.ProductUi

object CategoryGroupContract {
    data class UiState(
        val products: List<ProductUi> = emptyList(),
        val searchQuery: String = "",
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    sealed class UiAction {
        object ClearError : UiAction()
        data class ChangeQuery(val query: String) : UiAction()
        data class SearchProducts(val query: String) : UiAction()
        data class LoadProducts(val products: List<ProductUi>) : UiAction()
    }

    sealed class UiEffect {
        data class ShowError(val message: String) : UiEffect()
        object NavigateBack : UiEffect()
        data class OpenDetail(val productId: Int) : UiEffect()
        data class ShowToast(val message: String) : UiEffect()
    }
}