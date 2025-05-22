package com.gulsenurgunes.furfriends.ui.categorygroup

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.domain.usecase.GetAllProductsUseCase
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoryGroupContract.UiState(isLoading = true))
    val uiState: StateFlow<CategoryGroupContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect = Channel<CategoryGroupContract.UiEffect>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    private val categoryKey = savedStateHandle.get<String>("categoryKey").orEmpty()
    private val storeKey = "furfriends"

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val res = getProductsByCategory(storeKey)) {
                is Resource.Success -> {
                    Log.d("CategoryGroupVM", "💾 rawDataSize=${res.data.size}")
                    Log.d("CategoryGroupVM", "🔑 categoryKey=$categoryKey")
                    val filtered = res.data.filter {
                        it.title.contains(categoryKey, ignoreCase = true)
                    }
                    onAction(CategoryGroupContract.UiAction.LoadProducts(filtered))
                }
                is Resource.Error -> {
                    Log.e("CategoryGroupVM", "🛑 error=${res.message}")
                    _uiEffect.send(CategoryGroupContract.UiEffect.ShowError(res.message))
                    _uiState.update { it.copy(errorMessage = res.message) }
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun onAction(action: CategoryGroupContract.UiAction) {
        when (action) {
            is CategoryGroupContract.UiAction.ClearError -> {
                _uiState.update { it.copy(errorMessage = null) }
            }
            is CategoryGroupContract.UiAction.ChangeQuery -> {
                _uiState.update { it.copy(searchQuery = action.query) }
            }
            is CategoryGroupContract.UiAction.SearchProducts -> {
                val filtered = _uiState.value.products
                    .filter { it.title.contains(action.query, ignoreCase = true) }
                _uiState.update { it.copy(products = filtered) }
            }
            is CategoryGroupContract.UiAction.LoadProducts -> {
                _uiState.update { it.copy(products = action.products) }
            }
        }
    }


}