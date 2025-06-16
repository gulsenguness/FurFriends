package com.gulsenurgunes.furfriends.ui.home.homefirst

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.domain.usecase.order.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategories: GetCategoriesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeContract.UiState())
    val uiState: StateFlow<HomeContract.UiState> = _uiState.asStateFlow()

    private val _effect = Channel<HomeContract.UiEffect>(Channel.BUFFERED)
    val effect: Flow<HomeContract.UiEffect> = _effect.receiveAsFlow()

    init {
        loadCategories()
    }

    fun onAction(action: HomeContract.UiAction) {
        if (action is HomeContract.UiAction.LoadHome) {
            loadCategories()
        }
    }

    private fun loadCategories() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        when (val res = getCategories("FurFriends")) {
            is Resource.Success -> _uiState.update {
                it.copy(
                    categories = res.data,
                    isLoading  = false
                )
            }
            is Resource.Error -> {
                _effect.send(HomeContract.UiEffect.ShowError(res.message))
                _uiState.update { it.copy(isLoading = false, errorMessage = res.message) }
            }
        }
    }
}
