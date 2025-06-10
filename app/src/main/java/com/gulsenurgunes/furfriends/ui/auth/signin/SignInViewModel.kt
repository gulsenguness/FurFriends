package com.gulsenurgunes.furfriends.ui.auth.signin

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.domain.usecase.auth.SignInUseCase
import com.gulsenurgunes.furfriends.domain.usecase.auth.SignInWithGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val googleClient: GoogleSignInClient
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignInContract.UiState())
    val uiState: StateFlow<SignInContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect = Channel<SignInContract.UiEffect>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    fun onAction(a: SignInContract.UiAction) {
        when (a) {
            is SignInContract.UiAction.ChangeEmail ->
                update { copy(email = a.email, showEmailError = false) }

            is SignInContract.UiAction.ChangePassword ->
                update { copy(password = a.password, showPasswordError = false) }

            SignInContract.UiAction.SignInClick -> signIn()

            SignInContract.UiAction.GoogleSignInClick ->
                update { copy(googleIntent = googleClient.signInIntent) }

            is SignInContract.UiAction.GoogleSignInResult ->
                handleGoogleResult(a.intent)

            SignInContract.UiAction.ClearError ->
                update { copy(signInError = "") }
        }
    }

    private fun signIn() = viewModelScope.launch {
        val s = uiState.value
        val err = when {
            s.email.isBlank() -> "E-posta boş bırakılamaz"
            s.password.isBlank() -> "Parola boş bırakılamaz"
            else -> null
        }
        if (err != null) {
            update {
                copy(
                    showEmailError = s.email.isBlank(),
                    showPasswordError = s.password.isBlank(),
                    signInError = err
                )
            }
            _uiEffect.send(SignInContract.UiEffect.ShowError(err))
            return@launch
        }

        update { copy(isLoading = true) }
        when (val res = signInUseCase(s.email, s.password)) {
            is Resource.Success -> {
                _uiEffect.send(SignInContract.UiEffect.ShowSuccess)
                _uiEffect.send(SignInContract.UiEffect.GoHome)
                _uiState.value = SignInContract.UiState()
            }

            is Resource.Error -> {
                update { copy(isLoading = false, signInError = res.message) }
                _uiEffect.send(SignInContract.UiEffect.ShowError(res.message))
            }
        }
    }


    private fun handleGoogleResult(intent: Intent) = viewModelScope.launch {
        val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
        try {
            val acct = task.getResult(ApiException::class.java)!!
            update { copy(isLoading = true) }

            when (val res = signInWithGoogleUseCase(acct.idToken!!)) {
                is Resource.Success -> {
                    _uiEffect.send(SignInContract.UiEffect.ShowSuccess)
                    _uiEffect.send(SignInContract.UiEffect.GoHome)
                    _uiState.value = SignInContract.UiState()
                }

                is Resource.Error -> {
                    update { copy(isLoading = false, signInError = res.message) }
                    _uiEffect.send(SignInContract.UiEffect.ShowError(res.message))
                }
            }
        } catch (e: ApiException) {
            _uiEffect.send(
                SignInContract.UiEffect.ShowError(e.localizedMessage ?: "Google hatası")
            )
        }
    }


    private inline fun update(
        reducer: SignInContract.UiState.() -> SignInContract.UiState
    ) = _uiState.update(reducer)

}