package com.gulsenurgunes.furfriends.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulsenurgunes.furfriends.domain.model.FavoriteResponse
import com.gulsenurgunes.furfriends.domain.model.ProductUi
import com.gulsenurgunes.furfriends.domain.usecase.AddToFavoriteUseCase
import com.gulsenurgunes.furfriends.domain.usecase.GetFavoritesUseCase
import com.gulsenurgunes.furfriends.domain.usecase.RemoveFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavorites: GetFavoritesUseCase,
    private val addFavorites: AddToFavoriteUseCase,
    private val removeFavorites: RemoveFromFavoritesUseCase
) : ViewModel() {
    private val _favorites = MutableStateFlow<List<ProductUi>>(emptyList())
    val favorites: StateFlow<List<ProductUi>> = _favorites.asStateFlow()

    private val _actionStatus = MutableSharedFlow<FavoriteResponse>()
    val actionStatus: SharedFlow<FavoriteResponse> = _actionStatus

    fun loadFavorites(userId: String) {

    }

    fun toggleFavorite(userId: String, product: ProductUi) {
    }
}