package com.gulsenurgunes.furfriends.ui.auth.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.common.UIState
import com.gulsenurgunes.furfriends.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : ViewModel() {
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var signInState by mutableStateOf<UIState>(UIState.Idle)

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onSignInClick() {
        if (email.isBlank() || password.isBlank()) {
            signInState = UIState.Error("Email and password cannot be left blank.\n")
            return
        }
        viewModelScope.launch {
            signInState = UIState.Loading
            signInState = when (val res = signInUseCase(email, password)) {
                is Resource.Success ->
                    UIState.Success(res.data)

                is Resource.Error -> {
                    UIState.Error(res.message)
                }
            }
        }

    }

    fun resetState() {
        signInState = UIState.Idle
    }

}