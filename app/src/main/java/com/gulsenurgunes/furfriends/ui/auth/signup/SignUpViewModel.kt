package com.gulsenurgunes.furfriends.ui.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.common.UIState
import com.gulsenurgunes.furfriends.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
) : ViewModel() {
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var name by mutableStateOf("")
        private set
    var signUpState by mutableStateOf<UIState>(UIState.Loading(isLoading = true))
        private set


    fun onEmailChange(new: String) {
        email = new
    }


    fun onPasswordChange(password: String) {
        this.password = password

    }

    fun onNameChange(new: String) {
        name = new
    }


    fun onSignUpClick() {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            signUpState = UIState.Error("Lütfen tüm alanları doldurun.")
            return
        }
        viewModelScope.launch {
            signUpState = UIState.Loading(isLoading = true)
            signUpState = when (val res = signUpUseCase(name, email, password)) {
                is Resource.Success ->
                    UIState.Success(res.data)
                is Resource.Error ->
                    UIState.Error(res.message)
            }
        }
    }

    fun resetState() {
        signUpState = UIState.Loading(isLoading = true)
    }

}