package com.gulsenurgunes.furfriends.ui.home

import com.gulsenurgunes.furfriends.domain.model.ProductUi

object FoodContract {
    data class UiState(
        val allProducts: List<ProductUi> = emptyList(),
        val subCategories: List<String> = listOf("Dogs", "Cats", "Rabbits", "Parrot"),
        val selectedSubCategory: String? = null,
        val filteredProducts: List<ProductUi> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    sealed class UiAction {
        data object LoadFood : UiAction()
        data class SelectSubCategory(val category: String) : UiAction()
    }

    sealed class UiEffect {
        data class ShowError(val message: String) : UiEffect()
    }
}