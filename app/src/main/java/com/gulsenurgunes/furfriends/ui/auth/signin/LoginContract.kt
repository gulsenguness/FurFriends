package com.gulsenurgunes.furfriends.ui.auth.signin

import android.content.Intent
import android.content.IntentSender

object SignInContract {
    data class UiState(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
        val googleIntent: Intent? = null,
        val showEmailError: Boolean = false,
        val showPasswordError: Boolean = false,
        val signInError: String = ""
    )


    sealed class UiAction {
        data object SignInClick : UiAction()
        data class ChangeEmail(val email: String) : UiAction()
        data class ChangePassword(val password: String) : UiAction()
        data class GoogleSignInResult(val intent: Intent) : UiAction()
        data object GoogleSignInClick : UiAction()
        data object ClearError : UiAction()
    }

    sealed interface UiEffect {
        data class ShowError(val msg: String) : UiEffect
        object ShowSuccess : UiEffect
        object GoHome : UiEffect
    }
}
