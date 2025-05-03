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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.common.UIState
import com.gulsenurgunes.furfriends.domain.model.User
import com.gulsenurgunes.furfriends.ui.auth.components.AuthButton
import com.gulsenurgunes.furfriends.ui.auth.components.DividerWithText
import com.gulsenurgunes.furfriends.ui.auth.components.HeaderImage
import com.gulsenurgunes.furfriends.ui.auth.components.LabeledTextField
import com.gulsenurgunes.furfriends.ui.auth.components.ScreenHeader
import com.gulsenurgunes.furfriends.ui.auth.components.SocialIconsRow
import com.gulsenurgunes.furfriends.ui.auth.components.TextWithAction

@Composable
fun SignUpScreen(
    onSignUpSuccess: (User) -> Unit = {},
    onSignInClick: () -> Unit = {},
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {

    val email = signUpViewModel.email
    val password = signUpViewModel.password
    val name = signUpViewModel.name
    val signUpState = signUpViewModel.signUpState
    val snackbarHost = remember { SnackbarHostState() }

    LaunchedEffect(signUpState) {

        when (signUpState) {
            is UIState.Success -> {
                snackbarHost.showSnackbar("Kayıt yapıldı")
                onSignUpSuccess((signUpState).user)
                signUpViewModel.resetState()
            }

            is UIState.Error -> {
                snackbarHost.showSnackbar((signUpState).message)
            }

            else -> {}
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
                    value = name,
                    onValueChange = { signUpViewModel.onNameChange(it) },
                    errorMessage = if (name.isBlank() && signUpState is UIState.Error) "İsim boş bırakılamaz" else null
                )
                LabeledTextField(
                    label = "Email Address *",
                    value = email,
                    onValueChange = { signUpViewModel.onEmailChange(it) },
                    errorMessage = if (email.isBlank() && signUpState is UIState.Error) "E-posta boş bırakılamaz" else null
                )
                LabeledTextField(
                    label = "Password *",
                    value = password,
                    onValueChange = { signUpViewModel.onPasswordChange(it) },
                    isPassword = true,
                    errorMessage = if (password.isBlank() && signUpState is UIState.Error) "Password boş bırakılamaz" else null
                )
                CheckboxConditions()
                AuthButton(
                    text = "Sign Up",
                    onClick = { signUpViewModel.onSignUpClick() },
                    modifier = Modifier.padding(top = 16.dp),
                    enabled = name.isNotBlank() && email.isNotBlank() && password.isNotBlank()
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
}