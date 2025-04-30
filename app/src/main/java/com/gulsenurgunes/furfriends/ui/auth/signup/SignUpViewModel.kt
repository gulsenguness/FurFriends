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
    var e_mail by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var name by mutableStateOf("")
        private set
    var signUpState by mutableStateOf<UIState>(UIState.Idle)
        private set

    fun onEmailChange(email: String) {
        e_mail = email
    }

    fun onPasswordChange(password: String) {
        this.password = password
    }

    fun onNameChange(name: String) {
        this.name = name
    }

    fun onSignUpClick() {
        viewModelScope.launch {
            signUpState = UIState.Loading
            signUpState = when (val res = signUpUseCase(e_mail, password)) {
                is Resource.Success -> UIState.Success(res.data)
                is Resource.Error -> UIState.Error(res.message)
            }
        }
    }
}