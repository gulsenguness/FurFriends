package com.gulsenurgunes.furfriends.ui.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.domain.usecase.cart.AddToCartUseCase
import com.gulsenurgunes.furfriends.domain.usecase.favorites.AddToFavoriteUseCase
import com.gulsenurgunes.furfriends.domain.usecase.favorites.ObserveFavoriteIdsUseCase
import com.gulsenurgunes.furfriends.domain.usecase.favorites.RemoveFromFavoritesUseCase
import com.gulsenurgunes.furfriends.domain.usecase.order.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    observeFavorites: ObserveFavoriteIdsUseCase,
    private val getProducts: GetAllProductsUseCase,
    private val addToFavorites: AddToFavoriteUseCase,
    private val removeFromFavorites: RemoveFromFavoritesUseCase,
    private val addToCart: AddToCartUseCase,
    @Named("userId") private val userId: String,
    ) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteContract.UiState())
    val uiState: StateFlow<FavoriteContract.UiState> = _uiState.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        collectFavorites(observeFavorites())
    }

    private fun collectFavorites(idsFlow: Flow<Set<Int>>) = viewModelScope.launch {
        combine(
            idsFlow,
            flow { emit(getProducts("furfriends")) },
            selectedCategory,
            searchQuery
        ) { ids, res, category, query ->
            val allFavorites = (res as? Resource.Success)?.data.orEmpty()
                .filter { ids.contains(it.id) }
                .map { it.copy(isFavorite = true) }

            val filtered = allFavorites
                .filter { category == "All" || it.category.equals(category, ignoreCase = true) }
                .filter { it.title.contains(query, ignoreCase = true) }

            filtered
        }.collect { list ->
            _uiState.update { it.copy(isLoading = false, favoriteProducts = list) }
        }
    }

    fun onAction(action: FavoriteContract.UiAction) = viewModelScope.launch {
        when (action) {
            is FavoriteContract.UiAction.AddToFavorites -> {
                addToFavorites("defaultUser", action.productId.toString())
            }

            is FavoriteContract.UiAction.DeleteFromFavorites -> {
                removeFromFavorites("defaultUser", action.productId.toString())
            }

            is FavoriteContract.UiAction.AddToCart -> {
                addToCart(userId, action.product.id)
            }

            is FavoriteContract.UiAction.LoadFavorites -> Unit
        }
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}