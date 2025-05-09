package com.gulsenurgunes.furfriends.common

import com.gulsenurgunes.furfriends.domain.model.User

sealed class UIState {
    data class Loading(val isLoading: Boolean) : UIState()
    data class Success(val user: User) : UIState()
    data class Error(val message: String) : UIState()
}