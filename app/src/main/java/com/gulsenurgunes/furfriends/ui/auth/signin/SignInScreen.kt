package com.gulsenurgunes.furfriends.ui.auth.signin

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
fun SignInScreen(
    onForgotPasswordClick: () -> Unit,
    onHomeClick: () -> Unit,
    onSignUpClick: () -> Unit,
    signInViewModel: SignInViewModel = hiltViewModel()
) {

    val uiState by signInViewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHost = remember { SnackbarHostState() }


    LaunchedEffect(Unit) {
        signInViewModel.uiEffect.collect { eff ->
            when (eff) {
                SignInContract.UiEffect.GoHome      -> onHomeClick()
                SignInContract.UiEffect.ShowSuccess -> snackbarHost.showSnackbar("Hoş geldin 🎉")
                is SignInContract.UiEffect.ShowError-> snackbarHost.showSnackbar(eff.msg)
            }
        }
    }

    uiState.googleIntent?.let { intent ->
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { res ->
            res.data?.let {
                signInViewModel.onAction(SignInContract.UiAction.GoogleSignInResult(it))
            }
        }
        LaunchedEffect(intent) { launcher.launch(intent) }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHost) }
    ) { padding ->

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HeaderImage(R.drawable.animal1, "Sign In Header Image")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start
            ) {
                ScreenHeader(
                    title = "Sign In To Your Account",
                    subtitle = "Welcome Back You've Been Missed"
                )
                Spacer(modifier = Modifier.height(24.dp))
                LabeledTextField(
                    label = "Email Address *",
                    value = uiState.email,
                    onValueChange = {
                        signInViewModel.onAction(SignInContract.UiAction.ChangeEmail(it))
                    },
                    errorMessage = if (uiState.showEmailError) "E-posta boş bırakılamaz" else null
                )
                LabeledTextField(
                    label = "Password *",
                    value = uiState.password,
                    onValueChange = {
                        signInViewModel.onAction(SignInContract.UiAction.ChangePassword(it))
                    },
                    isPassword = true,
                    errorMessage = if (uiState.showPasswordError) "Parola boş bırakılamaz" else null
                )
                TextButton(
                    onClick = { onForgotPasswordClick() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Forgot Password?")
                }
                Spacer(modifier = Modifier.height(16.dp))
                AuthButton(
                    text = if (uiState.isLoading) "Loading…" else "Sign In",
                    onClick = { signInViewModel.onAction(SignInContract.UiAction.SignInClick) },
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = Color.Black,
                    contentColor   = Color.White,
                    shape  = RoundedCornerShape(12.dp),
                    height = 48.dp,
                    enabled = !uiState.isLoading
                )
                DividerWithText("Or Sign In With")
                SocialIconsRow(
                    onGoogleClick   = { signInViewModel.onAction(SignInContract.UiAction.GoogleSignInClick) },
                    onFacebookClick = { },
                    onAppleClick = { }
                )
                TextWithAction(
                    text = "Not a member?",
                    actionText = "Create an account",
                    onActionClick = { onSignUpClick() })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    SignInScreen(onForgotPasswordClick = {}, onHomeClick = {}, onSignUpClick = {})
}