package com.gulsenurgunes.furfriends.ui.categorygroup

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.domain.model.ProductUi
import com.gulsenurgunes.furfriends.domain.usecase.AddToFavoriteUseCase
import com.gulsenurgunes.furfriends.domain.usecase.GetAllProductsUseCase
import com.gulsenurgunes.furfriends.domain.usecase.ObserveFavoriteIdsUseCase
import com.gulsenurgunes.furfriends.domain.usecase.RemoveFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryGroupViewModel @Inject constructor(
    private val getProductsByCategory: GetAllProductsUseCase,
    private val addToFavorites: AddToFavoriteUseCase,
    private val removeFromFavorites: RemoveFromFavoritesUseCase,
    observeFavoriteIds: ObserveFavoriteIdsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoryGroupContract.UiState(isLoading = true))
    val uiState: StateFlow<CategoryGroupContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect = Channel<CategoryGroupContract.UiEffect>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    private val favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    private val categoryKey = savedStateHandle.get<String>("categoryKey").orEmpty()
    private val storeKey = "furfriends"

    init {
        viewModelScope.launch {                                                  // ⭐
            observeFavoriteIds().collect { ids ->
                favoriteIds.value = ids
                _uiState.update { state ->
                    state.copy(
                        products = state.products.map { p ->
                            p.copy(isFavorite = ids.contains(p.id))
                        }
                    )
                }
            }
        }

        loadProducts()
    }


    private fun loadProducts() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        when (val res = getProductsByCategory(storeKey)) {
            is Resource.Success -> {
                val filtered = res.data
                    .filter { it.title.contains(categoryKey, ignoreCase = true) }
                    .map { it.copy(isFavorite = favoriteIds.value.contains(it.id)) } // ⭐
                _uiState.update { it.copy(products = filtered) }
            }
            is Resource.Error -> {
                _uiState.update { it.copy(errorMessage = res.message) }
            }
        }
        _uiState.update { it.copy(isLoading = false) }
    }


    fun toggleFavorite(product: ProductUi) = viewModelScope.launch {              // ⭐
        _uiState.update { state ->
            state.copy(
                products = state.products.map { p ->
                    if (p.id == product.id) p.copy(isFavorite = !p.isFavorite) else p
                }
            )
        }
        if (favoriteIds.value.contains(product.id)) {
            removeFromFavorites("defaultUser", product.id.toString())
        } else {
            addToFavorites("defaultUser", product.id.toString())
        }
    }

}