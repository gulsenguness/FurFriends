package com.gulsenurgunes.furfriends.ui.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.ui.auth.components.AuthButton
import com.gulsenurgunes.furfriends.ui.auth.components.DividerWithText
import com.gulsenurgunes.furfriends.ui.auth.components.HeaderImage
import com.gulsenurgunes.furfriends.ui.auth.components.LabeledTextField
import com.gulsenurgunes.furfriends.ui.auth.components.ScreenHeader
import com.gulsenurgunes.furfriends.ui.auth.components.SocialIconsRow
import com.gulsenurgunes.furfriends.ui.auth.components.TextWithAction

@Composable
fun SignUpScreen(
    onNavigateHome: () -> Unit,
    onSignInClick: () -> Unit = {},
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by signUpViewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHost = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        signUpViewModel.uiEffect.collect { eff ->
            when (eff) {
                SignUpContract.UiEffect.ShowSuccess      -> snackbarHost.showSnackbar("Kayıt başarılı 🎉")
                is SignUpContract.UiEffect.ShowError     -> snackbarHost.showSnackbar(eff.msg)
                SignUpContract.UiEffect.GoToMainScreen   -> onNavigateHome()
            }

        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHost) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize()) {
            HeaderImage(
                R.drawable.animal2,
                "Sign Up Header Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start
            ) {
                ScreenHeader(
                    title = "Create Your Account",
                    subtitle = "Welcome back! Please enter your details"
                )
                Spacer(modifier = Modifier.height(24.dp))
                LabeledTextField(
                    label = "Name *",
                    value = uiState.name,
                    onValueChange = {
                        signUpViewModel.onAction(SignUpContract.UiAction.ChangeName(it))
                    },
                    errorMessage = if (uiState.showNameError) "İsim boş bırakılamaz" else null
                )
                LabeledTextField(
                    label = "Email Address *",
                    value = uiState.email,
                    onValueChange = {
                        signUpViewModel.onAction(SignUpContract.UiAction.ChangeEmail(it))
                    },
                    errorMessage = if (uiState.showEmailError) "E-posta boş bırakılamaz" else null
                )

                LabeledTextField(
                    label = "Password *",
                    value = uiState.password,
                    onValueChange = {
                        signUpViewModel.onAction(SignUpContract.UiAction.ChangePassword(it))
                    },
                    isPassword = true,
                    errorMessage = if (uiState.showPasswordError) "Parola boş bırakılamaz" else null
                )

                CheckboxConditions()
                AuthButton(
                    text = "Sign Up",
                    onClick = { signUpViewModel.onAction(SignUpContract.UiAction.SignUpClick) },
                    modifier = Modifier.padding(top = 16.dp),
                )
                DividerWithText("Or Sign Up With")
                SocialIconsRow(onGoogleClick = {})
                TextWithAction(
                    text = "Already have and account? ",
                    actionText = "Sign In",
                    onActionClick = onSignInClick
                )
            }
        }
    }
    uiState.signUpError.takeIf { it.isNotBlank() }?.let { msg ->
        LaunchedEffect(msg) {
            snackbarHost.showSnackbar(msg)
            signUpViewModel.onAction(SignUpContract.UiAction.ClearError)
        }
    }
}