package com.gulsenurgunes.furfriends.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.domain.usecase.AddToFavoriteUseCase
import com.gulsenurgunes.furfriends.domain.usecase.GetFavoritesUseCase
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
class FavoritesViewModel @Inject constructor(
    private val getFavorites: GetFavoritesUseCase,
    private val addFavorites: AddToFavoriteUseCase,
    private val removeFavorites: RemoveFromFavoritesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteContract.UiState())
    val uiState: StateFlow<FavoriteContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect = Channel<FavoriteContract.UiEffect>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        loadFavorites()
    }

    fun onAction(action: FavoriteContract.UiAction) {
        when(action) {
            is FavoriteContract.UiAction.AddToFavorites -> addFavorite(action.productId)
            is FavoriteContract.UiAction.DeleteFromFavorites -> deleteFavorite(action.productId)
            is FavoriteContract.UiAction.LoadFavorites -> Unit
        }
    }


    private fun loadFavorites(userId: String = "defaultUser") = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        when(val res = getFavorites(userId)) {
            is Resource.Success -> _uiState.update { it.copy(isLoading=false, favoriteProducts = res.data) }
            is Resource.Error   -> {
                _uiState.update { it.copy(isLoading=false, errorMessage = res.message) }
                _uiEffect.send(FavoriteContract.UiEffect.ShowError(res.message))
            }
        }
    }

    private fun addFavorite(productId: Int, userId: String = "defaultUser") = viewModelScope.launch {
        _uiState.update { it.copy(isLoading=true, addToFavorites = null, errorMessage = null) }
        when(val res = addFavorites(userId, productId.toString())) {
            is Resource.Success -> {
                _uiState.update { it.copy(isLoading=false, addToFavorites = res.data) }
                loadFavorites(userId)
            }
            is Resource.Error -> {
                _uiState.update { it.copy(isLoading=false, errorMessage = res.message) }
                _uiEffect.send(FavoriteContract.UiEffect.ShowError(res.message))
            }
        }
    }


    private fun deleteFavorite(productId: Int, userId: String = "defaultUser") {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, deleteFromFavorites = null, errorMessage = null) }

            when (val res = removeFavorites(userId, productId.toString())) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            deleteFromFavorites = res.data
                        )
                    }
                    loadFavorites(userId)
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = res.message)
                    }
                    _uiEffect.send(FavoriteContract.UiEffect.ShowError(res.message))
                }
            }
        }
    }
}