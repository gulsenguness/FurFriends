package com.gulsenurgunes.furfriends.common

import com.gulsenurgunes.furfriends.domain.model.User

sealed class UIState {
  data object Idle : UIState()
  data object Loading : UIState()
  data class Success(val user: User) : UIState()
  data class Error(val message: String) : UIState()
}