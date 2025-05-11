package com.gulsenurgunes.furfriends.common

import com.gulsenurgunes.furfriends.data.source.remote.model.User

sealed class UIState<T> {
    data class Loading(val isLoading: Boolean) : UIState<Any?>()
    data class Success(val user: User) : UIState<Any?>()
    data class Error(val message: String) : UIState<Any?>()
}