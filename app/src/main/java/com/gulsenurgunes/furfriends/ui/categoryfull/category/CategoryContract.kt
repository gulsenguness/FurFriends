package com.gulsenurgunes.furfriends.ui.categoryfull.category

import com.gulsenurgunes.furfriends.domain.model.Category
import com.gulsenurgunes.furfriends.domain.model.ProductUi

object CategoryContract {
    data class UiState(
        val categoryList: List<Category> = emptyList(),
        var searchQuery: String = "",
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
    )

    sealed class UiAction {
        data object ClearError : UiAction()
        data class SearchProducts(val query: String) : UiAction()
        data class LoadProducts(val categoryList: List<ProductUi>) : UiAction()
        data class ChangeQuery(val query: String) : UiAction()
    }

    sealed class UiEffect {
        data class ShowError(val message: String) : UiEffect()
        data object NavigateBack : UiEffect()
        data class DetailScreen(val id: Int) : UiEffect()
    }
}