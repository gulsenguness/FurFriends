package com.gulsenurgunes.furfriends.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulsenurgunes.furfriends.common.UIState
import com.gulsenurgunes.furfriends.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading(isLoading = true))
    val uiState: StateFlow<UIState> = _uiState

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            delay(1_000)
            _uiState.value = UIState.Success(user = User("", "", "", ""))
        }
    }

    fun retry() {
        _uiState.value = UIState.Loading(isLoading = true)
        load()
    }
}