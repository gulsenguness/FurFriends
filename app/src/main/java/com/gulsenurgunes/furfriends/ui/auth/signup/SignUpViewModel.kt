package com.gulsenurgunes.furfriends.ui.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.domain.usecase.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.channels.Channel



@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpContract.UiState())
    val uiState: StateFlow<SignUpContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect = Channel<SignUpContract.UiEffect>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    fun onAction(action: SignUpContract.UiAction) {
        when (action) {
            is SignUpContract.UiAction.ChangeEmail ->
                update { copy(email = action.email) }

            is SignUpContract.UiAction.ChangePassword ->
                update { copy(password = action.password) }

            is SignUpContract.UiAction.ChangeName ->
                update { copy(name = action.name) }

            is SignUpContract.UiAction.ChangeSurname ->
                update { copy(surname = action.surname) }

            SignUpContract.UiAction.SignUpClick ->
                signUp()

            SignUpContract.UiAction.ClearError ->
                update { copy(signUpError = "") }
        }
    }

    private fun signUp() = viewModelScope.launch {
        val s = uiState.value
        if (!validate(s)) return@launch

        update { copy(isLoading = true, signUpError = "") }

        when (val res = signUpUseCase(s.name, s.email, s.password)) {
            is Resource.Success -> {
                _uiEffect.send(SignUpContract.UiEffect.ShowSuccess)     // ✅ başarı
                _uiEffect.send(SignUpContract.UiEffect.GoToMainScreen)  // ✅ navigate
                _uiState.value = SignUpContract.UiState()               // ✅ formu sıfırla
            }

            is Resource.Error -> {
                update { copy(isLoading = false, signUpError = res.message) }
                _uiEffect.send(SignUpContract.UiEffect.ShowError(res.message)) // ✅ hata
            }
        }
    }

    private inline fun update(block: SignUpContract.UiState.() -> SignUpContract.UiState) =
        _uiState.update(block)

    private fun validate(s: SignUpContract.UiState): Boolean {
        val msg = when {
            s.name.isBlank()     -> "İsim boş bırakılamaz"
            s.email.isBlank()    -> "E-posta boş bırakılamaz"
            s.password.isBlank() -> "Parola boş bırakılamaz"
            else                 -> null
        }

        if (msg != null) {
            update {
                copy(
                    showNameError     = s.name.isBlank(),
                    showEmailError    = s.email.isBlank(),
                    showPasswordError = s.password.isBlank(),
                    signUpError       = msg
                )
            }
            _uiEffect.trySend(SignUpContract.UiEffect.ShowError(msg))
            return false
        }
        return true

    }


}








