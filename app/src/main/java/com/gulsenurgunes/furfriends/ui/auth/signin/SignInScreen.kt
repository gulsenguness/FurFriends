package com.gulsenurgunes.furfriends.ui.auth.signin

import android.app.Activity
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
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.common.UIState
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
    val email = signInViewModel.email
    val password = signInViewModel.password
    val uiState = signInViewModel.signInState
    val snackbarHost = remember { SnackbarHostState() }

    val googleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { intent ->
                    val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                    try {
                        val acct = task.getResult(ApiException::class.java)!!
                        signInViewModel.firebaseAuthWithGoogle(acct.idToken!!)
                    } catch (e: ApiException) {
                        signInViewModel.signInState =
                            UIState.Error("Google sign-in failed: ${e.localizedMessage}")
                    }
                }
            }
        }
    )


    LaunchedEffect(uiState) {
        when (uiState) {
            is UIState.Success -> {
                snackbarHost.showSnackbar("Giriş Başarılı")
                onHomeClick()
                signInViewModel.resetState()
            }

            is UIState.Error -> {
                val result = snackbarHost.showSnackbar(uiState.message)
                if (result == SnackbarResult.Dismissed || result == SnackbarResult.ActionPerformed) {
                    signInViewModel.resetState()
                }
            }

            else -> {
            }
        }
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
                    value = email,
                    onValueChange = { signInViewModel.onEmailChange(it) },
                    errorMessage = if (email.isBlank() && uiState is UIState.Error)
                        "E-posta boş bırakılamaz"
                    else null

                )
                LabeledTextField(
                    label = "Password *",
                    value = password,
                    onValueChange = { signInViewModel.onPasswordChange(it) },
                    isPassword = true,
                    errorMessage = if (password.isBlank() && uiState is UIState.Error)
                        "Parola boş bırakılamaz"
                    else null

                )
                TextButton(
                    onClick = { onForgotPasswordClick() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Forgot Password?")
                }
                Spacer(modifier = Modifier.height(16.dp))
                AuthButton(
                    text = "Sign In",
                    onClick = { signInViewModel.onSignInClick() },
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(12.dp),
                    height = 48.dp,
                    enabled = email.isNotBlank() && password.isNotBlank()
                )
                DividerWithText("Or Sign In With")
                SocialIconsRow(
                    onGoogleClick = { googleLauncher.launch(signInViewModel.getGoogleSignInIntent()) },
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