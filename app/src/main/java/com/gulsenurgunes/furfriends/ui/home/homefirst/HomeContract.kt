package com.gulsenurgunes.furfriends.ui.home.homefirst

import com.gulsenurgunes.furfriends.domain.model.Category
import com.gulsenurgunes.furfriends.domain.model.ProductUi

object HomeContract {
    data class UiState(
        val isLoading: Boolean = false,
        val categories: List<Category>      = emptyList(),
        val featuredProducts: List<ProductUi> = emptyList(),
        val errorMessage: String? = null
    )

    sealed class UiAction {
        data object LoadHome : UiAction()
    }

    sealed class UiEffect {
        data class ShowError(val message: String) : UiEffect()
        data class ProductCartClick(val id: Int) : UiEffect()
        data object SearchClick : UiEffect()
        data class CategoryClick(val category: String) : UiEffect()
    }
}
